# 키친포스

## 요구 사항
### 상품 (Product)
- [ ] 상품을 등록할 수 있다.
  * 상품의 가격이 올바르지 않으면 등록할 수 없다.
    * 상품의 가격은 0 원 이상이어야 한다.
  * 상품의 목록을 조회할 수 있다.

### 메뉴 그룹 (Menu Group)
- [ ] 메뉴 그룹을 등록할 수 있다.
- [ ] 전체 메뉴 그룹을 조회할 수 있다.

### 메뉴 (Menu)
- [ ] 메뉴를 등록할 수 있다.
  - 메뉴 가격은 0원 이상이어야 한다.
  - 메뉴 그룹이 지정 되어야 한다.
  - 메뉴의 가격은 메뉴상품 총합보다 낮아야 한다.
  - 메뉴에 추가할 상품이 등록되어 있어야한다.
- [ ] 전체 메뉴를 조회할 수 있다.

### 주문 (Order)
- [ ] 주문을 등록할 수 있다.
  - 주문 항목이 비어있으면 등록할 수 없다.
  - 주문 항목의 메뉴가 존재해야 한다.
  - 주문 항목의 주문테이블이 존재해야 한다.
  - 주문 테이블은 빈 테이블이 아니어야 한다.
  - 주문테이블, 주문시간, 주문항목을 등록하고 주문상태는 COOKING으로 변경한다.
- [ ] 전체 주문을 조회할 수 있다.
- [ ] 주문 상태를 변경할 수 있다.
  - 유효한 주문인지 체크한다.
    - 주문이 등록되어 있어야 한다.
  - 주문상태가 COMPLETION 이면 변경할 수 없다.
  
### 주문 테이블 (Order Table)
- [ ] 주문테이블을 등록할 수 있다.
  - 테이블 그룹은 null 로 초기화 한다.
- [ ] 전체 테이블 목록를 조회할 수 있다.
- [ ] 테이블을 비울 수 있다.
  - 주문테이블이 존재해야 한다.
  - 단체 테이블이면 초기화할 수 없다.
  - 주문테이블의 주문상태가 COOKING, MEAL이면 초기화할 수 없다.
- [ ] 테이블의 손님 수를 변경할 수 있다.
  - 변경할 손님 수는 0명 이상이어야 한다.
  - 주문 테이블이 유효해야 한다.
  - 주문 테이블이 비어 있으면 안 된다.

### 테이블 그룹 (Table Group)
- [ ] 단체 테이블을 등록한다.
  - 주문 테이블이 비어 있으면 안 된다. 
  - 주문 테이블이 두 개 이상이어야 한다.
  - 단체 테이블로 지정할 주문 테이블들은 미리 등록되어 있어야 한다.
  - 주문 테이블은 빈 테이블이 아니어야 한다.
  - 단체로 지정된 테이블이 아니어야 한다.
- [ ] 단체 테이블을 해제한다.
  - 주문 테이블 중 하나라도 주문상태가 COOKING, MEAL 이면 해제할 수 없다.


## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

## 1단계 - 테스트를 통한 코드보호
### [요구사항](https://edu.nextstep.camp/s/X02BsEA0/ls/uvktq6et)
- `kitchenpos` 패키지 코드를 분석하여 요구사항을 `README.md`에 작성한다.
- 모든 Business Object에 대한 테스크 코드를 `@SpringBootTest` 를 이용한 통합 테스트 코드 또는 `@ExtendWith(MockitoExcention.class)`를 이용한 단위 테스트 코드를 작성한다.
- Lombok 없이 미션을 진행한다.


### 구현
- [x] 요구사항 작성
- [X] 테스트 코드 구현

## 2단계 - 서비스 리팩터링
### [요구사항](https://edu.nextstep.camp/s/X02BsEA0/ls/eTPlycZ2)
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현한다.
