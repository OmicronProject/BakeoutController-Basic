package kernel.models;

import kernel.controllers.variables.VariableProviderContainer;

/**
 * Similar to {@link CombinedDeviceContainer}, this interface bundles
 * together the view and the controller for a variable provider container
 */
interface CombinedVariableProviderContainer extends kernel.views.VariableProviderContainer, VariableProviderContainer {}
