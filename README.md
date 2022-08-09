# help-desk-mvc
The backend for the HelpDesk web-app.   
The application was developed without using SpringBoot, SpringData or Lombock.
Used technologies:
* SpringMVC, 
* JPA/Hibernate,
* H2,
* Java 8, 
* Tomcat    
The task was completed according to the following stories:  
_____

### User Authentication

#### Story #1.1
As a User,    
I want to securely log into an application,    
So that I can have data/functionality depending on my permission level (relevant to me).    
     
Acceptance Criteria:    
A User here can be the following:    
* An Employee    
* A Manager    
* An Engineer    
In order to log in, it is required to enter an email and a password.    
If one of them or both are blank while logging in, the system notifies the User near the corresponding field: "Please fill out the required field."    

### Ticket List

#### Story #2.1
As a User,
I want to be able to view tickets relevant to me,
So that I can track and/or process them.

Acceptance Criteria:    
A User here can be the following:    
* An Employee    
* A Manager    
* An Engineer   
A Ticket List contains the tickets created by Users:    
The Employee is able to see the tickets created by him in all statuses.    
The Manager is able to see:    
 - the tickets created by him in all statuses
- the tickets created by all Employees in New
 - the tickets which have him as an Approver in statuses Approved, Declined, Cancelled, In Progress, Done
The Engineer is able to see:    
- the tickets created by all Employees and Managers in statuses Approved
- the tickets which have him as an Assignee in statuses In Progress and Done.
The List contains the following attributes in the following order:     
- ID
- Name
- Desired Date
- Urgency
- Status
- Action    
By default, the Ticket List is sorted:     
- Primarily, by Priority (Critical -> High -> Medium -> Low)
- Secondarily, by Desired Date descending.

### Ticket Creation

#### Story #3.1
As a User, 
I want to create a new ticket, 
So that I can track the progress of the task related to it.

Acceptance Criteria:
A User here can be the following:
* An Employee
* A Manager
The User is able to open the Ticket Creation page from the Ticket List.    
The User is able to select a Category of the ticket.    
The Category can be one of the following:    
 - Application & Services
 - Benefits & Paper Work
 - Hardware & Software
 - People Management
 - Security & Access
 - Workplaces & Facilities
The User is able to enter a Name of the ticket.    
The User is able to enter a Description of the ticket.    
The User is able to select an Urgency of the ticket.    
The Urgency can be one of the following:    
 - Critical 
 - High
 - Medium
 - Low    
The User is able to select a Desired Resolution Date of the ticket.    
The User is able to add attachments to the ticket.    
It is possible to remove an attached file.     
The User is able to add a comment to the ticket.    
The User is able to save the ticket as a draft.    
The ticket has the ‘Draft’ status.    
The User is able to submit the ticket.    
The following fields shall be filled out before submission:    
- Category
- Name
- Urgency    
The ticket has the ‘New’ status.    

### Ticket Overview

### Ticket Edition

#### Story #5.1
As a User, 
I want to edit a ticket, 
So that I can keep the ticket up-to-date.

Acceptance Criteria:    
A User here can be the following:    
* An Employee
* A Manager

### Ticket Status Transition

#### Story #6.1
As a User,
I can transmit a ticket to different statuses, 
So that I can track the progress on a ticket. 

Acceptance Criteria:    
A User here can be the following:    
* An Employee
* A Manager
* An Engineer
The ticket status can be one of the following:
- Draft
- New
- Approved
- Declined
- Cancelled
- In Progress
- Done
The following actions are available for the Users:

![image](https://user-images.githubusercontent.com/110538351/182604917-05cc35ce-fa2c-43e9-a0c8-1b7136c3bf2a.png)


The actions transmit the ticket to the following statuses:
- ‘Create’ to ‘Draft’
- ‘Submit’ to ‘New’
- ‘Approve’ to ‘Approved’
- ‘Decline’ to ‘Declined’
- ‘Cancel’ to ‘Cancelled’
- ‘Assign to Me’ to ‘In Progress’
- ‘Done’ to ‘Done’

### History

#### Story #7.1
As a User, 
I want to have the History of the Ticket,
So that I can track any activity related to a ticket.
    
Acceptance Criteria:
A User here can be the following:
- An Employee
- A Manager
- An Engineer
The History contains the following attributes in the following order:    
- Timestamp
- User
- Action
- Description (more detailed information about an action)
    
- Timestamp – User – Ticket is created - Ticket is created.
________
- Timestamp – User – Ticket is edited - Ticket is edited.
________
- Timestamp – User – Ticket Status is changed - Ticket Status is changed from [X] to [Y].
________ 
- Timestamp – User – File is attached - File is attached: [File Name]. 
_________
- Timestamp – User – File is removed - File is removed: [File Name].
_________
- The History is disabled for editing.

### Feedback

#### Story #8.1
As a User, 
I want to provide feedback regarding my ticket,
So that I can let an Engineer know about the quality of his work.

Acceptance Criteria:
A User here can be the following:
- An Employee
 -A Manager
The User is able to provide a feedback for the ticket done.    
Providing feedback is available only for tickets in Done.    
Providing feedback is available only for the User who created the ticket.     
The User is able to view the provided feedback.    
While viewing the provided feedback is disabled for editing.    

### Notification Emails

#### Story #9.1
As a User, 
I want to receive a notification email when an important event happens in the system,
So that I will not miss it.

Acceptance Criteria:
A User here can be the following:
- An Employee
- A Manager
- An Engineer
The system sends notification email in the following cases:

![image](https://user-images.githubusercontent.com/110538351/182605150-214a366d-d4f5-4cc4-af7d-243860878c22.png)


The email has the following templates:    
**Template #1:**    
Subject: New ticket for approval    
Body:    
Dear Managers,    
    
New ticket **[Ticket ID via link]** is waiting for your approval.    
    
**Template #2:**    
Subject: Ticket was approved    
Body:    
Dear Users,    
    
Ticket **[Ticket ID via link]** was approved by the Manager.    
    
**Template #3:**    
Subject: Ticket was declined    
Body:    
Dear **[Recipient First Name] [Recipient Last Name]**,    
    
Ticket **[Ticket ID via link]** was declined by the Manager.    
    
**Template #4:**    
Subject: Ticket was cancelled    
Body:    
Dear **[Recipient First Name] [Recipient Last Name]**,    
    
Ticket **[Ticket ID via link]** was cancelled by the Manager.    
    
**Template #5:**        
Subject: Ticket was cancelled    
Body:    
Dear Users,    
    
Ticket **[Ticket ID via link]** was cancelled by the Engineer.    
    
**Template #6:**    
Subject: Ticket was done    
Body:    
Dear **[Recipient First Name] [Recipient Last Name]**,    
    
Ticket **[Ticket ID via link]** was done by the Engineer.    
Please provide your feedback clicking on the ticket ID.    
    
**Template #7:**    
Subject: Feedback was provided    
Body:    
Dear **[Recipient First Name] [Recipient Last Name]**,    
    
The feedback was provided on ticket **[Ticket ID via link]**.    
Following by the link in the email the User goes to the Ticket Overview page.    
