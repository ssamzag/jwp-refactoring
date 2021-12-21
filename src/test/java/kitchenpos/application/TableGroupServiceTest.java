package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderTableDao orderTableDao;

    @Mock
    private TableGroupDao tableGroupDao;

    @InjectMocks
    private TableGroupService tableGroupService;

    @DisplayName("테이블 그룹 생성")
    @Test
    void 테이블_그룹_생성() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, null, 4, true);
        OrderTable orderTable2 = new OrderTable(2L, null, 3, true);
        List<OrderTable> orderTables = Lists.newArrayList(orderTable1, orderTable2);

        TableGroup tableGroup = new TableGroup(1L, null, orderTables);

        given(orderTableDao.findAllByIdIn(anyList())).willReturn(orderTables);
        given(tableGroupDao.save(any())).willReturn(tableGroup);
        given(orderTableDao.save(any())).willReturn(orderTable1, orderTable2);

        // when
        TableGroup result = tableGroupService.create(tableGroup);

        // then
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getOrderTables()).hasSize(2);
        assertThat(result.getOrderTables().get(0).getId()).isEqualTo(1L);
        assertThat(result.getOrderTables().get(0).getTableGroupId()).isEqualTo(1L);
        assertThat(result.getOrderTables().get(0).getNumberOfGuests()).isEqualTo(4);
        assertThat(result.getOrderTables().get(0).isEmpty()).isFalse();
        assertThat(result.getOrderTables().get(1).getId()).isEqualTo(2L);
        assertThat(result.getOrderTables().get(1).getTableGroupId()).isEqualTo(1L);
        assertThat(result.getOrderTables().get(1).getNumberOfGuests()).isEqualTo(3);
        assertThat(result.getOrderTables().get(1).isEmpty()).isFalse();
    }

    @DisplayName("테이블 그룹 생성시 주문 테이블 목록이 빈 경우 예외 발생")
    @Test
    void 테이블_그룹_생성_예외1() {
        // given
        TableGroup tableGroup = new TableGroup(1L, null, Lists.emptyList());

        // when, then
        assertThatIllegalArgumentException().isThrownBy(
            () -> tableGroupService.create(tableGroup)
        );
    }

    @DisplayName("테이블 그룹 생성시 주문 테이블 목록이 1개만 있는 경우 예외 발생")
    @Test
    void 테이블_그룹_생성_예외2() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, null, 4, true);
        TableGroup tableGroup = new TableGroup(1L, null, Lists.newArrayList(orderTable1));

        // when, then
        assertThatIllegalArgumentException().isThrownBy(
            () -> tableGroupService.create(tableGroup)
        );
    }

    @DisplayName("테이블 그룹 생성시 빈 테이블이 아닌 경우 예외 발생")
    @Test
    void 테이블_그룹_생성_예외3() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, null, 4, false);
        OrderTable orderTable2 = new OrderTable(2L, null, 3, true);
        List<OrderTable> orderTables = Lists.newArrayList(orderTable1, orderTable2);

        TableGroup tableGroup = new TableGroup(1L, null, orderTables);

        given(orderTableDao.findAllByIdIn(anyList())).willReturn(orderTables);


        // when, then
        assertThatIllegalArgumentException().isThrownBy(
            () -> tableGroupService.create(tableGroup)
        );
    }

    @DisplayName("테이블 그룹 생성시 이미 그룹이 있는 테이블인 경우 예외 발생")
    @Test
    void 테이블_그룹_생성_예외4() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, 1L, 4, true);
        OrderTable orderTable2 = new OrderTable(2L, null, 3, true);
        List<OrderTable> orderTables = Lists.newArrayList(orderTable1, orderTable2);

        TableGroup tableGroup = new TableGroup(1L, null, orderTables);

        given(orderTableDao.findAllByIdIn(anyList())).willReturn(orderTables);


        // when, then
        assertThatIllegalArgumentException().isThrownBy(
            () -> tableGroupService.create(tableGroup)
        );
    }

    @DisplayName("테이블 그룹 해제")
    @Test
    void 테이블_그룹_해제() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, 1L, 4, true);
        OrderTable orderTable2 = new OrderTable(2L, 1L, 3, true);
        List<OrderTable> orderTables = Lists.newArrayList(orderTable1, orderTable2);

        given(orderTableDao.findAllByTableGroupId(any())).willReturn(orderTables);
        given(orderDao.existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList())).willReturn(false);

        // when
        tableGroupService.ungroup(1L);

        // then
        verify(orderTableDao, times(2)).save(any());
    }

    @DisplayName("주문 상태가 요리중 또는 식사일 때 테이블 그룹 해제를 하면 예외 발생")
    @Test
    void 테이블_그룹_해제_예외() {
        // given
        OrderTable orderTable1 = new OrderTable(1L, 1L, 4, true);
        OrderTable orderTable2 = new OrderTable(2L, 1L, 3, true);
        List<OrderTable> orderTables = Lists.newArrayList(orderTable1, orderTable2);

        given(orderTableDao.findAllByTableGroupId(any())).willReturn(orderTables);
        given(orderDao.existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList())).willReturn(true);

        // when, then
        assertThatIllegalArgumentException().isThrownBy(
            () -> tableGroupService.ungroup(1L)
        );
    }
}