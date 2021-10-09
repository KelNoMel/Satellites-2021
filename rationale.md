# Rationale - Assignment

# NetworkObject
This is the superclass of both the generic device and satellite class.

This class exists because for both device and satellite, the majority of their fields are shared.

In fact, in very simple terms a satellite could be considered an orbiting device.

This is why they share common methods which also allow them to interact similarly through the NetworkObject superclass

This allows for streamlining of code and less repetition. For example, some functions in blackoutController do not need seperate Device and Satellite implementations, but a streamlined NetworkObject implementation.

# File
The file class is doesn't extend any other classes or interfaces, and has an aggregate relationship to Satellites and Devices.

This is because Files cannot exist outside a File list field in a NetworkObject class.

# Device
Extends the NetworkObject class

The Device has no added fields over its' superclass

Nevertheless, it's useful to have a generic Device class to have common methods and to give casting options for subclasses.

Could experiment later with implementing it as an interface with subclasses extending directly from NetworkObject, but doesn't look very organised that way

# Device subclasses
These are mostly the Device class that have a unique constructor with hardcoded fields reflecting the Device variation they represent

# HandHeldDevice
Extends Device

Has a unique constructor

# DesktopDevice
Extends Device

Has a unique constructor

# LaptopDevice
Extends Device

Has a unique constructor

# Satellite
Extends the NetworkObject class

The Satellite has byte/file storage limits as well as upload/download limits. However its' most defining field is the speed field, which is integral in the travel() method allowing it to orbit in simulation

Since most satellites orbit similarly (same direction/formula), we have a shared travel() method. However this is ovewritten by the RelaySatellite which has its' own pattern.

The manageStorage() method allows all satellites to keep within their storage limits, it's performed when files are altered (simulate() and sendFile()).
All satellites perform this method, which also accounts for no limit fields

# Satellite Subclasses

# StandardSatellite
Extends Satellite

Has a unique constructor

# Shrinking Satellite
Extends Satellite

Has a unique constructor

# RelaySatellite
Extends Satellite

Possesses a new field, direction. Since it can change direction when it reaches its' patrol boundary, it flips between 1 and -1 to signify direction change (useful in calculating position change)
