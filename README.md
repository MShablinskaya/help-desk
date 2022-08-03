# help-desk-mvc
Apparently, the backend code for the HelpDesk application is presented.    
The task was completed according to the following stories    
The application was developed without using SpringBoot, SpringData or Lombock.
Used technologies SpringMVC, Hibernate, H2, Java 8, Tomcat
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
The email meets the following requirements:    
* It is allowed to enter any Unicode character.
* Email contains at least one @.
* Email contains at least one dot. 
* It is not allowed to have a leading or trailing @. 
* It is not allowed to have a leading or a trailing dot. 
* The maximum number of characters is 100.
* The password meets the following requirements:
* It is allowed to enter any Unicode character.
* It is required to contain at least one uppercase and one lowercase English alpha characters, one digit and one special character.
* Special characters could be the following:
    ~."(),:;<>@[]!#$%&'*+-/=?^_`{|}    
* The minimum number of characters is 6.
* The maximum number of characters is 20.
* The password is masked.
* If either an entered email or password is invalid, the system notifies the User: "Please make sure you are using a valid email or password".
* If both an entered email and a password are valid, the system allows the User to log in.
* The User is able to log out the application.
* The User is redirected to the Login page.    

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


#### Story #2.2
As a User, 
I want to be able to quickly sort a list of tickets by various criteria, 
So that I can quickly find what I want.

Acceptance Criteria:
A User here can be the following:
* An Employee    
* A Manager    
* An Engineer
It is possible to sort by ID ascending and descending.
It is possible to sort by Name in alphabetic order and in inverted alphabetic order.
It is possible to sort by Desired Date ascending and descending.
It is possible to sort by Urgency in the following order:
Critical -> High -> Medium -> Low
Low -> Medium -> High -> Critical
It is possible to sort by Status in alphabetic order and in inverted alphabetic order.
It is obvious for the user what criterion the Ticket List is sorted by.


#### Story #2.3
As a User, 
I want to be able to quickly filter a list of tickets by various criteria, 
So that I can quickly find what I want.

Acceptance Criteria:
A User here can be the following:
* An Employee    
* A Manager    
* An Engineer
It is possible to filter by the following attributes:
ID
Name
Desired Date
Urgency
Status 
The system start filtering after at least one characters has entered.
It is allowed to enter upper and lowercase English alpha characters, digits and special characters: ~."(),:;<>@[]!#$%&'*+-/=?^_`{|}
The User is able to use the ‘My Tickets’ filter:
The Employee is able to see tickets created by him.
The Manager is able to see tickets created by him and approved by him in Approved
The Engineer is able to see tickets assigned to him.
The User is able to return to the full Ticket List.

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
 Application & Services
 Benefits & Paper Work
 Hardware & Software
 People Management
 Security & Access
 Workplaces & Facilities
The User is able to enter a Name of the ticket.
It is allowed to enter lowercase English alpha characters, digits and special characters: ~."(),:;<>@[]!#$%&'*+-/=?^_`{|}
The maximum number of characters is 100.
The User is able to enter a Description of the ticket.
It is allowed to enter upper and lowercase English alpha characters, digits and special characters: ~."(),:;<>@[]!#$%&'*+-/=?^_`{|}
The maximum number of characters is 500.
The User is able to select an Urgency of the ticket.
The Urgency can be one of the following:
 Critical 
 High
 Medium
 Low
The User is able to select a Desired Resolution Date of the ticket.
The format: DD/MM/YYYY.
The User is not able to select a date that is less than the current date.
The User is able to add attachments to the ticket.
It is allowed to attach the following file formats: pdf, doc, docx, png, jpeg, jpg.
If the User selects a file with an invalid extension, the system notifies him:
“The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.”
The max file size the User can attach is 5 Mb.
If the size of an attached file exceeds the maximum, the system notifies the User:
“The size of attached file should not be greater than 5 Mb. Please select another file.”
It is possible to download an attached file. 
It is possible to remove an attached file. 
The User is able to add a comment to the ticket.
It is allowed to enter upper and lowercase English alpha characters, digits and special characters: ~."(),:;<>@[]!#$%&'*+-/=?^_`{|}
The maximum number of characters is 500.
The system does not allow to enter invalid characters.
The User is able to save the ticket as a draft.
The ticket has the ‘Draft’ status.
The User is able to submit the ticket.
The following fields shall be filled out before submission:
Category
Name
Urgency
The ticket has the ‘New’ status.
The User is able to return to the Ticket List from the Ticket Creation page. 
After saving as a draft or submitting the system closes the Ticket Creation page and displays the Tickets List.

### Ticket Overview

#### Story #4.1
As a User, 
I want to get full information on the particular ticket,
So that I can make decisions and process the ticket.

Acceptance Criteria:
A User here can be the following:
* An Employee    
* A Manager    
* An Engineer
The User is able to open the Ticket Overview page from the Ticket List.
Ticket overview contain the following information:
Name
Created on (Creation Date)
 The format: DD/MM/YYYY.
Status
Category
Urgency
Description
Desired resolution date
 The format: DD/MM/YYYY.
Owner (an Employee or Manager who created the ticket)
 Owner is assigned to the ticket after the actions: ‘Save as Draft’ or ‘Submit’.
Approver (a Manager who approved, declined or canceled the ticket)
 Approver is assigned to the ticket after the actions: ‘Approve, ‘Decline’ or ‘Cancel’.
Assignee (the Engineer who was assigned to the ticket)
Assignee is assigned to the ticket after the actions: ‘Assign to Me’ or ‘Cancel’.
Attachments
The User is able to download Attachments.
All the information is disabled for editing.
The User is able to view the History. 
By default, the User is able to view top 5 newest records.
The User is able to view the full History.
See other related detailed requirements in Story #7.1.
The User is able to view the Comments of the ticket.
The Comment List contains the following attributes in the following order:
 Timestamp
 User
 Comment (text)
By default, the User is able to view top 5 latest comments.
The User is able to view the full Comments List.
The User is able to add a comment to the ticket.
 See detailed requirements in Story #3.1 AC 9.1, 9.2.
The User is able to return to the Ticket List from the Ticket Overview page. 

### Ticket Edition

#### Story #5.1
As a User, 
I want to edit a ticket, 
So that I can keep the ticket up-to-date.

Acceptance Criteria:
A User here can be the following:
An Employee
A Manager
The User is able to edit the ticket in the Draft status.
The User is able to open the Ticket Edition page from the Ticket Overview page.
The User is able to edit the following:
Category
See detailed requirements in Story #3.1 AC 3.1
Name
See detailed requirements in Story #3.1 AC 4.1, 4.2
Description
See detailed requirements in Story #3.1 AC 5.1, 5.2
Urgency
See detailed requirements in Story #3.1 AC 6.1
Desired resolution date
See detailed requirements in Story #3.1 AC 7.1, 7.2
Attachments
See detailed requirements in Story #3.1 AC 8.1 - 8.4
The User is able to add a comment to the ticket.
See detailed requirements in Story #3.1 AC 9.1, 9.2
See other detailed requirements in Story #3.1 AC 10 - 12.
The User is able to return to the Ticket Overview page from the Ticket Edition page.
After saving as a draft or submitting the system closes the Ticket Edition page and displays the Ticket Overview page.

### Ticket Status Transition

#### Story #6.1
As a User,
I can transmit a ticket to different statuses, 
So that I can track the progress on a ticket. 

Acceptance Criteria:
A User here can be the following:
An Employee
A Manager
An Engineer
The ticket status can be one of the following:
Draft
New
Approved
Declined
Cancelled
In Progress
Done
The following actions are available for the Users:

![image](https://user-images.githubusercontent.com/110538351/182604917-05cc35ce-fa2c-43e9-a0c8-1b7136c3bf2a.png)


The actions transmit the ticket to the following statuses:
‘Create’ to ‘Draft’
‘Submit’ to ‘New’
‘Approve’ to ‘Approved’
‘Decline’ to ‘Declined’
‘Cancel’ to ‘Cancelled’
‘Assign to Me’ to ‘In Progress’
‘Done’ to ‘Done’

### History

#### Story #7.1
As a User, 
I want to have the History of the Ticket,
So that I can track any activity related to a ticket.

Acceptance Criteria:
A User here can be the following:
An Employee
A Manager
An Engineer
The History contains the following attributes in the following order:
Timestamp
User
Action
Description (more detailed information about an action)
When a ticket is created a record is added to the History with the following details: 
Timestamp – User – Ticket is created - Ticket is created.
When a ticket is edited a record is added to the History with the following details: 
Timestamp – User – Ticket is edited - Ticket is edited.
When a ticket is changed its status a record is added to the History with the following details: 
Timestamp – User – Ticket Status is changed - Ticket Status is changed from [X] to [Y].
When a file is attached a ticket a record is added to the History with the following details: 
Timestamp – User – File is attached - File is attached: [File Name]. 
When a file is removed from a ticket a record is added to the History with the following details: 
Timestamp – User – File is removed - File is removed: [File Name].
By default, the records are sorted by Timestamp descending.
The History is disabled for editing.

### Feedback

#### Story #8.1
As a User, 
I want to provide feedback regarding my ticket,
So that I can let an Engineer know about the quality of his work.

Acceptance Criteria:
A User here can be the following:
An Employee
A Manager
The User is able to provide a feedback for the ticket done.
Providing feedback is available only for tickets in Done.
Providing feedback is available only for the User who created the ticket.
The User is able to evaluate the work done by the Engineer regarding the ticket on a scale from 1 to 5.
This input is required for submission.
The User is able to add a comment in addition to the rate.
This input is optional for submission.
The User is able to view the provided feedback.
While viewing the provided feedback is disabled for editing.
After submission the system closes the Feedback page and opens a previous page.
The User is able to return to a previous page from the Feedback page.

### Notification Emails

#### Story #9.1
As a User, 
I want to receive a notification email when an important event happens in the system,
So that I will not miss it.

Acceptance Criteria:
A User here can be the following:
An Employee
A Manager
An Engineer
The system sends notification email in the following cases:

![image](https://user-images.githubusercontent.com/110538351/182605150-214a366d-d4f5-4cc4-af7d-243860878c22.png)


The email has the following templates:
Template #1:
Subject: New ticket for approval
Body:
Dear Managers,

New ticket [Ticket ID via link] is waiting for your approval.
Template #2:
Subject: Ticket was approved
Body:
Dear Users,

Ticket [Ticket ID via link] was approved by the Manager.
Template #3:
Subject: Ticket was declined
Body:
Dear [Recipient First Name] [Recipient Last Name],

Ticket [Ticket ID via link] was declined by the Manager.
Template #4:
Subject: Ticket was cancelled
Body:
Dear [Recipient First Name] [Recipient Last Name],

Ticket [Ticket ID via link] was cancelled by the Manager.
Template #5:
Subject: Ticket was cancelled
Body:
Dear Users,

Ticket [Ticket ID via link] was cancelled by the Engineer.
Template #6:
Subject: Ticket was done
Body:
Dear [Recipient First Name] [Recipient Last Name],

Ticket [Ticket ID via link] was done by the Engineer.
Please provide your feedback clicking on the ticket ID.
Template #6:
Subject: Feedback was provided
Body:
Dear [Recipient First Name] [Recipient Last Name],

The feedback was provided on ticket [Ticket ID via link].
Following by the link in the email the User goes to the Ticket Overview page.
