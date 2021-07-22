# Rakshak

> For all time, Always.

## `Section Links`

> [Introduction](#introduction)  
> [Problem Statement](#problem-statement)  
> [Features](#features)  
> [Future Scope](#future-scope)  
> [Tech Stack](#tech-stack)  
> [Database Structure](#database-structure)  
> [Contributors](#contributors)  
> [Suggest](#suggestions)  


## `Introduction`

Rakshak is a java based Integrated Hospital Management which contains both InPatient and OutPatient Department Functionalities (IPD & OPD) along with medical store and blood bank for the patients. It also contains employee management system for the staff to handle their employement life. It is completely built using java and its associated libraries.  

## `Problem-Statement`

In this time of pandemic the we all tried to move towards social distancing and make everything contactless. Due to which use of digital technologies increased drastically. Especially in the hospitals, which felt like the place of god in this pandemic. But the truth is not everyone is techy and immediately learns how to use a specific technology. So if we make the entire process digital it would be hard for some people to use that. So the problem was to make dynamic, easy to use, up to date, and All in one Hospital Management System. Following is my solution to this.  

## `Features`  

> This application consists of separate dashboards for the Patients, Doctors, Staff, Receptionists, and Admins.
> Following are the specific features related to all these.  

### `Authentication`

- Each of the user would be able to login to their account but only a patient can signup to their account (As others are staff members and their is different process for them to join the application).
- Patients are required to login with their Aadhaar Number and Staff members are provided with unique staff IDs.
- Authentication Security involves the `AES` Encryption Technology.
- After logging in user will be redirected to the dashboard specific for them.

### `Patient Dashboard`

- On the patient dashboard there are whole bunch of features related to the Patients.
- A patient can:
  1. Schedule an Appointment with a doctor.
  2. Check the Time Table of the Hospital.
  3. Check the availability of beds (COVID Ward, General Ward, ICU Ward).
  4. Access the medical store.
  5. Register for vaccine.
  6. Register for covid-19 test.
  7. Access Blood Bank.
     1. Request Blood.
     2. Donate Blood.
  8. Access Plasma Bank.
     1. Request Plasma.
     2. Donate Plasma.

### `Staff Dashboard`

- It has many staff related features.
- Such as:
  1. Mark their attendance.
  2. Check their attendance sheet.
  3. Paymanager:
     1. Current Payment Details.
     2. Yearly Payment Statistics.
     3. Leaves and Bonuses.
  4. Edit their Details.
  5. Check the Time Table of the Hospital.

### `Doctor Dashboard`

- Doctor Specific Features like:
  1. Dashboard has a list of scheduled appointments in the current shift of doctors (Time Table of doctors is divided into shifts).
  2. Remove appointment after checking the patient.
  3. Access Medical Store info.
  4. See their shifts and time table.
  5. Button to access their `Staff Dashboard`.

### `Receptionist Dashboard`

- Main Hospital Functionalities like:
  - OPD:
    1. Approve Appointments according to the availability.
    2. Check Doctor Availability and timetable.
    3. Approve blood and plasma requests according to the availability.
    4. Schedule an appointment for a patient.
  - IPD:
    1. Check bed availability.
    2. Book bed for the patient as prescribed by the doctor in the specific ward.
    3. Clear bed when the patient checks out.
    4. Access medical store.
  - Others:
    1. Access their staff dashboards.
    2. Register a patient.
    3. Check the timetable of the hospital.

### `Admin Dashboard`

- The admin manages the whole application and its data.
- Admin Dashboard consists of:
  1. Add, remove, edit staff details.
  2. Add, remove, edit bed details.
  3. Add, remove, edit vaccines available.
  4. Add, remove, edit medicines.
  5. Register Staff, Receptionist, Doctor, Admin.
  6. Change paymanager of staff members.
  7. Edit timetable of doctors.

## `Future-Scope`

- Publish the application on multiple platforms.
- Create a web app connected to the same server to provide flexibility.
- Implement Payment inside the application for medicine, appointment, etc.
- Add separate Blood bank and plasma bank portals.
- Add more IPD features.

## `Tech-Stack`

## `Database-Structure`

## `Contributors`

## `Suggestions`
