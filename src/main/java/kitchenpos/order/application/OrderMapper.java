package kitchenpos.order.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Order mapFrom(OrderRequest orderRequest) {
        return new Order(
                orderRequest.getOrderTableId(),
                OrderStatus.COOKING,
                toOrderLineItems(orderRequest.getOrderLineItems()));
    }

    private OrderLineItems toOrderLineItems(List<OrderLineItemRequest> orderLineItemRequests) {
        List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
                .map(OrderLineItem::of)
                .collect(Collectors.toList());

        return new OrderLineItems(orderLineItems);
    }
}
