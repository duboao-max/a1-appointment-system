# A1-bodu — Health Service Appointment System

PROG2004 Object Oriented Programming — Assessment 1

A simple appointment system for a health service, built in Java. The system
models health professionals (of several distinct types), patients, and
appointments, and provides management operations (add, view, filter, sort,
and cancel) while preventing double-booking.

## Project structure

```
A1-bodu/
├── src/
│   ├── AssignmentOne.java      # main method: end-to-end demonstration
│   ├── HealthProfessional.java # abstract base class (Part 1)
│   ├── GeneralPractitioner.java# concrete type (Part 2)
│   ├── Pediatrician.java       # concrete type (Part 2)
│   ├── Nurse.java              # concrete type (Part 2)
│   ├── Dietitian.java          # concrete type (Part 2)
│   ├── Patient.java            # patient model (Part 4)
│   ├── Appointment.java        # appointment model (Part 4)
│   └── AppointmentManager.java # collection management (Part 5)
└── README.md
```

## How to run

```bash
javac src/*.java
java -cp src AssignmentOne
```

## The four pillars of OOP

- **Abstraction** — `HealthProfessional` is an abstract class that captures the
  common data and behaviour of every professional.
- **Encapsulation** — all fields are `private` and exposed only through
  controlled accessors; invalid states are rejected in constructors.
- **Inheritance** — `GeneralPractitioner`, `Pediatrician`, `Nurse`, and
  `Dietitian` extend `HealthProfessional`, reusing shared code.
- **Polymorphism** — professionals are processed together through the common
  `HealthProfessional` type, and each presents its own details via overridden
  methods.
