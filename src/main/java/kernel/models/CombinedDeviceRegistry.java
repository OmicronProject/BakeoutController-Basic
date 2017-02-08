package kernel.models;

/**
 * Wraps both the view and controller into a combined interface for easier
 * writing.
 */
interface CombinedDeviceRegistry extends kernel.controllers
        .DeviceRegistry, kernel.views.DeviceRegistry {
}
