package com.acme.corp.tracker.extension;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationContext.Stage;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
class TrackerTickHandler implements OperationStepHandler {

    public static final TrackerTickHandler INSTANCE = new TrackerTickHandler();

    private TrackerTickHandler() {
    }

    @Override
    public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
        //Update the model
        final String suffix = PathAddress.pathAddress(operation.get(ModelDescriptionConstants.ADDRESS)).getLastElement().getValue();
        final long tick = operation.require("value").asLong();
        ModelNode node = context.readResourceForUpdate(PathAddress.EMPTY_ADDRESS).getModel();
        node.get("tick").set(tick);

        //Add a step to perform the runtime update
        context.addStep(new OperationStepHandler() {
            @Override
            public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
                TrackerService service = (TrackerService)context.getServiceRegistry(true).getRequiredService(TrackerService.createServiceName(suffix)).getValue();
                service.setTick(tick);
                context.completeStep();
            }
        }, Stage.RUNTIME);
        context.completeStep();
    }
}
