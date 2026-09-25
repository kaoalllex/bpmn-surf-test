# bpmn-surf-test

Manual-test sandbox for the [bpmn-surf](https://github.com/kaoalllex/bpmn-surf)
Chrome extension. Nothing here is a real system — the diagrams and the code exist
only so the extension has something to resolve, diff and navigate.

## Layout

| Path | What it is for |
|------|----------------|
| `order-service/` | Camunda **external tasks**: every service task states `camunda:topic`, the handlers are Kotlin/Java classes annotated with `@ExternalTaskSubscription` |
| `billing-service/` | Classic **JavaDelegates**: service tasks reference `camunda:class` or `camunda:delegateExpression`, the delegates are Java classes |
| `broken/` | Deliberately malformed diagrams (loading must fail cleanly, without leaking the document into the problem report) |
| `legacy/` | One oversized diagram, for performance and rendering under load |

## Call graph

```
OrderMainProcess
├── FulfillmentProcess
│   └── DeliveryProcess
└── PaymentProcess
    ├── PaymentRiskMatrix (DMN)
    ├── FeeSchedule (DMN)
    └── DunningProcess          <- lives in the other module
BillingProcess
└── DunningProcess
```

## Deliberate traps

- `findItems` is a prefix of `findItemsInCatalog` — a blob search for the first
  matches both handlers.
- `com.example.billing.delegate.RefundDelegate` and
  `com.example.billing.legacy.RefundDelegate` share a simple name.
- `${billingService.settle(execution)}` is a method expression, so no handler
  class can be derived from it.
- `migrationStep01…30` in `legacy/` have no handler code at all.
- `PaymentRiskMatrixV2` is referenced only from a branch, not from `main`.
