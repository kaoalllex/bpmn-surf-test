# bpmn-surf-test

Manual-test sandbox for the [bpmn-surf](https://github.com/kaoalllex/bpmn-surf)
Chrome extension. Nothing here is a real system — the diagrams and the code exist
only so the extension has something to resolve, diff and navigate.

## Layout

| Path | What it is for |
|------|----------------|
| `order-service/` | Camunda **external tasks**: every service task states `camunda:topic`, the handlers are Kotlin/Java classes annotated with `@ExternalTaskSubscription` |
| `order-service-c8/` | The **Camunda 8 (Zeebe)** twin of `order-service`: `zeebe:taskDefinition` job types, `zeebe:calledElement` / `zeebe:calledDecision`, I/O mappings, headers, user tasks with forms, FEEL. Workers are Kotlin/Java methods annotated with `@JobWorker`. Every id a search keys on (process, decision, message) carries a `C8` suffix, so the two modules never answer each other's searches |
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

OrderMainC8                     <- order-service-c8
├── FulfillmentC8
│   └── DeliveryC8
└── PaymentC8
    ├── PaymentRiskC8 (DMN)
    ├── FeeScheduleC8 (DMN)
    └── DunningC8               <- not defined anywhere
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

## Camunda 8 traps (`order-service-c8/`)

- `find-items` / `find-items-in-catalog` and `charge-customer` /
  `charge-customer-with-retry`: the job-type prefix traps, in Kotlin and in Java.
- `@JobWorker` with no `type` (`screenFraud`, `packItem`): the job type is the
  method name.
- `@JobWorker(timeout = 30_000, type = "reserve-stock")`: `type` is not the first
  argument; `find-items-in-catalog` spans several lines.
- `escalate-delivery` is served by the deprecated `@ZeebeWorker`: found only when
  that annotation name is configured in the extension.
- `archive-delivery` has no worker at all; `Notify the payment provider` is an HTTP
  connector (`io.camunda:http-json:1`) and must not get a handler badge.
- "Order completed published" is a message throw event whose job type sits on the
  event itself, not on its message event definition.
- `audit-step` is an execution listener's job type, not a task's.
- `DunningC8` is called but defined nowhere.
- `PaymentEventsListener` publishes `OrderPaidC8` (`newPublishMessageCommand`),
  `ShipmentConsumer` correlates `ShipmentConfirmedC8` (`newCorrelateMessageCommand`).
