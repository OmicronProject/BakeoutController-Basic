package kernel.models;

import kernel.views.DeviceContainer;

/**
 * Wraps both the view and controller into a combined interface for easier
 * writing.
 */
interface CombinedDeviceContainer extends kernel.controllers
        .DeviceRegistry, DeviceContainer {
}
