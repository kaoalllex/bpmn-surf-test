package com.example.order.listener

import org.camunda.bpm.engine.delegate.DelegateTask
import org.camunda.bpm.engine.delegate.TaskListener
import org.springframework.stereotype.Component

/** A task listener of the "Pick a courier" user task. */
@Component
class AssignCourierListener : TaskListener {

    override fun notify(delegateTask: DelegateTask) {
        delegateTask.addCandidateGroup("couriers")
    }
}
