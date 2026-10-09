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
- `root-level.bpmn` sits in the repository root on purpose: ref/path parsing used
  to succeed only there, so it is the control case for every nested diagram.
- `order-service/.../order/QesApplication.bpmn` hides its changes inside
  subprocesses: the `test/subprocess-child-changes` MR touches only their children
  (a renamed task in the expanded `UZ Issuance`, a renamed task in the collapsed
  `Sign UZ` nested in it, a task added to the collapsed `Check documents`, one
  removed from the collapsed `Archive application`). Every enclosing subprocess
  must be marked as containing changes; `Notify client` is the untouched control.

GitHub mirror: main moved ahead of the open pull requests on purpose.
