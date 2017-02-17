package kernel.models;

/**
 * Created by mkononen on 16/02/17.
 */
interface CombinedVariableProviderRegistry extends kernel.views
        .VariableProviderRegistry, kernel.controllers.variables.VariableProviderRegistry
{
}
