package kitchenpos.tableGroup.domain;

import kitchenpos.table.domain.OrderTableUnGroupEvent;
import kitchenpos.table.domain.OrderTables;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TableGroup extends AbstractAggregateRoot<TableGroup> {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private LocalDateTime createdDate;
    @Embedded
    private OrderTables orderTables;

    public TableGroup() {
        this(null, new OrderTables());
    }

    public TableGroup(OrderTables orderTables) {
        this(null, orderTables);
    }

    public TableGroup(Long id, OrderTables orderTables) {
        this.id = id;
        this.orderTables = orderTables;
        orderTables.addTableGroup(this);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public OrderTables getOrderTables() {
        return orderTables;
    }

    public void ungroup() {
        registerEvent(new OrderTableUnGroupEvent(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableGroup that = (TableGroup) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCreatedDate(), that.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedDate());
    }
}
