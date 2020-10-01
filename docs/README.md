# Duke User Guide

## Table of Contents
1. [**Introduction**](#1-introduction)
1. [**Quick Start**](#2-quick-start)
1. [**Features**](#3-features)\
3.1. [Adding A Task : `todo`, `deadline`, `event`](#31-adding-a-task--todo-deadline-event)\
3.2. [Listing Tasks: `list`](#32-listing-tasks-list)\
3.3. [Marking A Task As Done: `done`](#33-marking-a-task-as-done-done)\
3.4. [Deleting A Task : `delete`](#34-deleting-a-task--delete)\
3.5. [Finding Tasks : `find`](#35-finding-tasks--find)\
3.6. [Exiting Duke : `bye`](#36-exiting-duke--bye)
1. [**FAQ**](#4-faq)
1. [**Command Summary**](#5-command-summary)

## 1. Introduction

Duke is for those who **prefer to use a desktop app for keeping track of tasks**.
More importantly, Duke is **optimised for the Command Line Interface** (CLI) which is beneficial if you can type fast.
If you have trouble tracking your deadlines and event dates, Duke can help you manage by saving your task list into 
a data file and loading it when you start Duke again. 

Interested? Jump to the [Section 2. “Quick Start”](#2-quick-start) to get started!

## 2. Quick Start
1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `duke.jar` [here]().

1. Copy the file to the folder you want to use as the home folder for Duke.

1. In the home folder for Duke, launch the jar file using the `java -jar duke.jar` command 
on Terminal (for macOS) or Command Prompt (for Windows) to start the app. 
If the setup is correct, you should see something like the below:

    ```
        ____________________________________________________________
        Created data/duke.txt to store future tasks.
        ____________________________________________________________
        
        ____________________________________________________________
        Hello dude! I'm Duke
        How can I help you?
        ____________________________________________________________   
    ```

1. To use Duke, simply type a valid command into the terminal and press <kbd>Enter</kbd> to run the command.\
    Example: Typing `list` command and pressing <kbd>Enter</kbd> will list tasks added to Duke.

1. Some example commands you can try:

    - `list` : Views all tasks added to Duke.
    - `deadline CS2113 Individual Project /by 2/10/2020 2359` : Adds a deadline called `CS2113 Individual Project`
    which is due on `2 October 2020, 11.59PM` to Duke.
    - `bye` : Exits Duke.

1. A summary of all the features available in Duke can be found in [Section 4. Command Summary](#4-command-summary).\
   Refer to [Section 3. "Features"](#3-features) for the detailed instructions of the various commands of Duke.  

## 3. Features 

***
:information_source: **Command Format**

- Words that are in `UPPER_CASE` are the parameters to be supplied by you.\
Example: in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo CS2113 Team Meeting`.
- The `INDEX` used in various commands is a number specifying the order of a task in the tasks list.
This number can be found on the left of a task after running `list` command.
You can refer to [Section 3.2. Listing Tasks: `list`](#32-listing-tasks-list) to have more information about `list` command.\
Below is an example of Task Index:

    ![Image of Task Index](./TaskIndex.png)

- Dates that you input to Duke should follow `D/M/YYYY` format.\
    Example: `1/10/2020` represents the date 1 October 2020.
- Time that you input to Duke should follow `HHMM` format where `HH` is hour in day (0-23)
and `MM` is minute in hour (0-59).\
    Example: `1600` represents the time 4:59PM.
- Parameters cannot be reordered.
    Example: If the command specifies `DESCRIPTION /by DATE TIME`,
    `/by DATE TIME DESCRIPTION` will result in an invalid command. 
    
***


### 3.1. Adding A Task : `todo`, `deadline`, `event`
Adds a task to Duke for tracking purposes.

Each task type in Duke is denoted by its abbreviation. Task completion is denoted by
`[/]` and `[X]` symbols where `[/]` indicates task has been done while 
`[X]` indicates task has not been done.

After adding a task to the task list, Duke will automatically save the list to
`[YOUR_DUKE_HOME_FOLDER]/data/duke.txt`.

There are 3 types of task that you can add to Duke:

- `todo` - a task with only a description, represented by `[T]` in Duke.

    Format: `todo DESCRIPTION`\
    Example of usage: `todo CS2113 Team Meeting`\
    Expected outcome:
    
    ```
         ____________________________________________________________
         Got it. I've added this task:
         [T][X] CS2113 Team Meeting
         Now you have 1 task in the list.
         ____________________________________________________________
    ```
  
- `deadline` - a task with a description and a deadline, represented by `[D]` in Duke.

    Format: `deadline DESCRIPTION /by DATE TIME`\
    Example of usage: `deadline CS2113 Individual Project /by 2/10/2020 2359`\
    Expected outcome:
    
    ```
         ____________________________________________________________
         Got it. I've added this task:
         [D][X] CS2113 Individual Project (by: 2 October 2020, 11.59PM)
         Now you have 2 tasks in the list.
         ____________________________________________________________
    ```
  
    |:bulb:| Although other date time formats are accepted, it is recommended for `DATE TIME` to follow `D/M/YYYY HHMM` format so that `find` command can have optimal performance.|
    |------|:-------------------------------------------|                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
   
- `event` - a task with a description and date and time of the event, represented by `[E]` in Duke.

    Format: `event DESCRIPTION /at DATE TIME`\
    Example of usage: `event Google Day 2020 /at 9/9/2020 1500`\
    Expected outcome:
    
    ```
         ____________________________________________________________
         Got it. I've added this task:
         [E][X] Google Day 2020 (at: 9 September 2020, 3.00PM)
         Now you have 3 tasks in the list.
         ____________________________________________________________
    ``` 
  
    | :bulb: | Although other date time formats are accepted, it is recommended for `DATE TIME` to follow `D/M/YYYY HHMM` format so that `find` command can have optimal performance.|
    |---|:--------------------------|   

### 3.2. Listing Tasks: `list`
Lists all tasks in Duke with numbering according to the order they are added to Duke.

Format: `list`\
Expected outcome:

```
    ____________________________________________________________
    Here is the list of your tasks:
    1. [T][X] CS2113 Team Meeting
    2. [D][X] CS2113 Individual Project (by: 2 October 2020, 11.59PM)
    3. [E][X] Google Day 2020 (at: 9 September 2020, 3.00PM)
    ____________________________________________________________
``` 

### 3.3. Marking A Task As Done: `done`
Marks a task in Duke as done.

After marking a task in task list as done, Duke will automatically save the list to
`[YOUR_DUKE_HOME_FOLDER]/data/duke.txt`.

Format: `done INDEX`\
Example of usage: `done 1`\
Expected outcome:

```
    ____________________________________________________________
    Nice! I've marked this task as done:
    [T][/] CS2113 Team Meeting
    ____________________________________________________________
``` 

| :bulb: | If the task has been marked as done previously, Duke will show a warning about the duplicated mark as done.|
|--------|:---------------|  

### 3.4. Deleting A Task : `delete`
Deletes a task from Duke.

After deleting a task from the task list, Duke will automatically save the list to
`[YOUR_DUKE_HOME_FOLDER]/data/duke.txt`.

Format: `delete INDEX`\
Example of usage: `delete 2`\
Expected outcome:

```
    ____________________________________________________________
    Noted. I've removed this task:
    [D][X] CS2113 Individual Project (by: 2 October 2020, 11.59PM)
    Now you have 2 tasks in the list.
    ____________________________________________________________
``` 

### 3.5. Finding Tasks : `find`
Finds tasks in Duke by searching for a keyword which can be a number,
a word, a phrase, a month, etc. Duke will filter and display tasks 
whose description or date time matches the keyword.

Format: `find KEYWORD`\
Example of usage: `find CS2113`\
Expected outcome:

```
    ____________________________________________________________
    Here is the list of matching tasks:
    1. [T][/] CS2113 Team Meeting
    ____________________________________________________________
```  

### 3.6. Exiting Duke : `bye`
Exits Duke.

Format: `bye`\
Expected outcome:

```
    ____________________________________________________________
    Bye buddy. Hope to see you again soon!
    ____________________________________________________________
``` 

## 4. FAQ
Below are the answers to some frequently asked questions about Duke:

**Q:** Can I use Duke on another operating systems apart from Windows?\
**A:** Yes. Duke is compatible with Windows, macOS and Linux. 

**Q:** How do I transfer my data to another Computer?\
**A:** Install Duke in the other computer and overwrite the empty data file 
`duke.txt` it creates in `data` folder with `duke.txt` of your previous Duke `data` folder.

## 5. Command Summary

Below is the summary of all the features available in Duke:

**Action** | **Format, Examples** 
|------|-----------------|
**Add** | - `todo DESCRIPTION`<br><br> Example: `todo CS2113 Team Meeting`<br><br> - `deadline DESCRIPTION /by DATE TIME`<br><br> Example: `deadline CS2113 Individual Project /by 2/10/2020 2359`<br><br> - `event DESCRIPTION /at DATE TIME`<br><br> Example: `event Google Day 2020 /at 9/9/2020 1500`
**List** | `list`
**Mark As Done** | `done INDEX`<br><br> Example: `done 1`
**Delete** | `delete INDEX`<br><br> Example: `delete 2`
**Find** | `find KEYWORD`<br><br> Example: `find CS2113`
**Exit** | `bye`
