# Kernel API {#kernel_api}

## Introduction
The BakeoutController kernel manages access to hardware. It runs in a 
separate thread from the UI, and allows for separation of concerns between 
rendering data and collecting it. The kernel also manages global application
 state.
  
The kernel singelton is instantiated at boot time. Other components should 
interact with the kernel through the API described in this document.

## The Kernel API

The API consists of a series of public interfaces, and getters to retrieve 
implementations of these interfaces. The root interface is 
[Kernel](@ref kernel.Kernel)

### Kernel Interfaces
* [Kernel](@ref kernel.Kernel)
    - [Kernel.getCommPortReporter](@ref kernel.Kernel#getCommPortReporter) 
    returns [kernel.views.CommPortReporter](@ref kerne.views.CommPortReporter)
    - [Kernel.getPortDriver](@ref kernel.Kernel#getPortDriver) returns a 
    driver for making bare RS232 serial port connections
    
* [CommPortReporter](@ref kernel.views.CommPortReporter) reports on the 
state of available serial ports in this application.