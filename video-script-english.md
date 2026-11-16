# PROG2004 A1 Video Script — English (≤ 5 minutes)

> Instructions: each block below is ready to read aloud. Anything in `[brackets]`
> is a screen-action cue for you — do not read it out.
> Keep a moderate, steady pace. Screen-record + your voice only; you do not
> need to appear on camera.

---

## Opening (about 20 seconds)

Hi everyone. This is my PROG2004 Assessment 1 — a health service appointment
system, written in Java.

What the system does is simple: a patient can book an appointment with
different types of health professionals for the current day. I designed nine
classes that work together to model the system, manage appointments, and
prevent the same doctor from being double-booked.

Today I want to focus on how the four pillars of object-oriented programming —
abstraction, encapsulation, inheritance, and polymorphism — are realised in my
code, and why I made those design choices.

---

## Pillar 1 — Abstraction (about 50 seconds)

[Switch to HealthProfessional.java, highlight `public abstract class`]

The first pillar is **abstraction**.

Abstraction means pulling out the common, essential features of a group of
things into a single "template", and leaving the specific differences to be
implemented by subclasses.

In my code, `HealthProfessional` is an abstract class. Whether it is a general
practitioner, a paediatrician, a nurse, or a dietitian, they all share the same
information: a numeric ID, a name, and a fixed set of working days each week.

[Point at the `abstract` keyword and the `getSpecialty()` method]

I declared it `abstract`, and I defined an abstract method called
`getSpecialty()`. This has one important benefit: it **stops anyone from
creating a generic health professional with no specialty** — because in the
real world such a person does not exist. Any concrete type must extend this
class and implement its own `getSpecialty()`, otherwise the code will not even
compile. That makes my design more robust.

---

## Pillar 2 — Encapsulation (about 50 seconds)

[Switch to the fields and getters in HealthProfessional.java]

The second pillar is **encapsulation**.

Encapsulation means hiding the data and exposing it only through controlled
methods, so that outside code cannot put an object into an invalid state.

[Point at the `private final` fields]

As you can see, all of my fields are `private`, and apart from `workingDays`
they are also `final` — they cannot be changed after construction. Outside
code can only read them through the getters `getId()`, `getName()`, and
`getWorkingDays()`. There are no setters, so nobody can tamper with the data.

[Point at the validation in the constructor]

More importantly, I validate in the constructor: for example, the ID must
contain digits only, and an empty name throws an exception immediately. As a
result, an object is either created correctly, or it is not created at all —
it can never end up in a half-broken state like an empty ID.

[Optional: point at `Collections.unmodifiableSet`]

I also store the working days in an unmodifiable collection and return a
read-only view, which adds another layer of data safety.

---

## Pillar 3 — Inheritance (about 50 seconds)

[Switch to GeneralPractitioner.java, highlight `extends HealthProfessional`]

The third pillar is **inheritance**.

Inheritance means a subclass automatically receives the fields and methods of
its parent class, so we reuse code instead of writing it again.

[Switch quickly across the four subclasses, pointing at each `extends`]

My `GeneralPractitioner`, `Pediatrician`, `Nurse`, and `Dietitian` all extend
`HealthProfessional`. They **automatically inherit** the storage and access
logic for the ID, name, and working days, as well as common behaviour like
`toString` and `equals`.

[Point at a subclass-specific field, e.g. Nurse's registrationLevel]

Each subclass only needs to add what is **unique** to it. For example, a nurse
has an extra registration level, and a paediatrician has a patient age range.
The shared information is written once in the parent class, so there is no
duplicated code. That is the value of inheritance: change the parent once, and
every subclass benefits.

---

## Pillar 4 — Polymorphism (about 60 seconds)

[Switch to AssignmentOne.java, highlight `List<HealthProfessional>` and the
for-each loop]

The fourth pillar is **polymorphism**.

Polymorphism means the same call behaves differently depending on the object
it is applied to.

[Point at the `professionals` list and `System.out.println(professional)`]

In my main method, I put nine professionals of different types into a single
`List<HealthProfessional>` — note that I am using the **parent type** to store
and process them all together. Then, with just one for-each loop and one
`println` call, I print every object.

The interesting part is that, even though `println` calls the same
`toString()`, each object prints **its own distinct details** because it
overrides `getSpecialty()` and `getDetails()` — the doctor shows "can prescribe
medication", the nurse shows "registration level, can take blood samples", and
the dietitian shows their specialisation.

[Optional: point at the `findType` generic method as another example]

This is the core value of polymorphism: I can program against the parent type.
If I want to add a new kind of health professional in the future, I barely need
to change any processing logic — as long as the new class extends
`HealthProfessional`, it fits into the system automatically. This is also the
answer to the assignment's question "how much code would need to change to add
a new type": almost none.

---

## Closing (about 30 seconds)

[Switch back to the project structure or the running output]

To summarise: I used **abstraction** to define a common template for health
professionals, **encapsulation** to protect the data, **inheritance** to let the
four subtypes reuse shared code, and **polymorphism** to handle and display
them all in a uniform way.

That is my appointment system. Thank you for watching.

---

## Appendix: pillar → code location quick reference

| Pillar | Key code | Location |
|---|---|---|
| Abstraction | `public abstract class HealthProfessional` | `HealthProfessional.java` |
| Abstraction | `public abstract String getSpecialty();` | `HealthProfessional.java` |
| Encapsulation | `private final String id;` etc. | `HealthProfessional.java` fields |
| Encapsulation | `if (id == null \|\| !id.matches("\\d+"))` | `HealthProfessional.java` constructor |
| Encapsulation | `Collections.unmodifiableSet(...)` | `HealthProfessional.java` constructor |
| Inheritance | `extends HealthProfessional` | the four subclasses |
| Inheritance | `super(id, name, workingDays)` | the four subclass constructors |
| Polymorphism | `List<HealthProfessional> professionals` | `AssignmentOne.java` |
| Polymorphism | `System.out.println(professional)` in the loop | `AssignmentOne.java` |
| Polymorphism | overridden `getSpecialty()` / `getDetails()` | the four subclasses |
