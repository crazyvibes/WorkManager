# WorkManager

-WorkManager, is the new Android background tasks management system came with Jetpack.

-We use work manager to run deferrable,that means capable of being post ponded background works .

-Most importantly , WorkManager provides the guarantee that the system will run the postponded task
 even if the app closed. 

-Work manager will take care of the logic to start our work under a variety of situations,

 even if the user navigate away from the app. 

-Before work manager, we used Firebase Jobdispatcher, Job scheduler

 And Alarm manager with Broadcast receivers to schedule tasks. Scheduling tasks was complex and time consuming.

 We had to write codes to deal with a lot of constraints and requirements. But now, we have work manger.

-All we need to do is pass the task to the work manager. It will decide which scheduler to use
 considering the requirement And it will take care of constraints. 

-If the device API level is less than 23, work manage will use a combination of BroadcastReceiver and AlarmManager.

 If the device API level is 23 or higher, work manager will choose JobScheduler.

-If our task uses firebase, work manger will choose Firebase Jobdispatcher. 

-Work manager offers a lot of advanced features. It Supports chained tasks. 

-Work manager provides status updates as LiveData.

-It allows us to set constraints on when the task runs. Work manager uses less amount of system
 resources like battery power. It handles compatibility with different Android versions. It supports
 asynchronous tasks.

-If also supports periodic tasks. WorkManager is not for tasks that needs to be run in a “background” thread but
 don’t need to survive process death.For normal background tasks we don’t need work manager, we can easily use coroutines or rxjava for them.

-These are the two type of work requests we use when schedule tasks with work manager. periodic work requests
 and one time work requests.

-Let’s say we have an app created for a sales team. Each salesman needs to see current stock count of the products.
 Therefore our app should have reqular updates from the main server.
 So, in this case repeatedly updating stock details is a task and with work manager we can set up .
 this task to repeatedly run, let’s say around 30 minutes time period.
 That type of work requests are called periodic work requests.

-Now, let’s say we have an app created for a survey team Each member will insert new survery records including images and 
 hit on save button to upload them to the main web server.
 So, in this case uploading servey details is a task. With the support of work manager,we can schedule this task to run at an approximate time
 considering the availability of the internet and the battery charge level of the device.
 This type of work requests are called one time work requests.

-These are the four basic steps of scheduling a task using work manager.Create a subclass of the Worker class. Creae a workRequest. 
Enque the request.And Finally get the status updates. 


----------------------oneTimeWorkerRequest---------------------------

STEPS:

/** I am going to start by creating a new Android Studio Project.

Select empty activity.

Let’s name this as WorkManager. I have selected the language as Kotlin. API level as 26.

Let’s click on the finish button. Now, first of all we need to add required dependencies to use work manager

to the app level build.gradle file. We can find work manger dependencies from this page.

Let’s sync the gradle.

Now, Let’s create a worker class. This class will be executed by the work manager instance when the conditions meet.

Let’s name this as UploadWorker. This class should extend the worker class.

This is the parent class. androidx.work.Worker.

We need to always implement do work

function of the worker class. Let’s generate it. And this worker class has two constructor parameters.

An instance of Context and and instance of WorkerParameters.

Alt plus enter to import the class.

Then, we need to pass them to the constructor of the parent class.

Now, inside this do work function, We are support to write the code to execute the deferrable background

task. But in this lesson, our goal is to understand workmanger basics properly.

So, for now let’s just add a for loop with a log to simulate that task.

Let’s add a log.

This do work function should always return a result. Return Result dot success.

So, this is for success, if any error happen

this function should return failure result. I am going to add a try catch block for that.

Now, let’s switch to main activity. Here I am creating a new function

Private fun setOneTimeWorkRequest.

Inside

this function, I am going to write codes to tell workmanager to perform the task.

Our task is a one time work request.

We will learn about periodic work request later.

So, we need to have a oneTimeWorkRequest instance.

val oneTimeWorkRequest equals. OneTimeWorkRequest.Builder

Here we need to add the worker class we just created.

Then, Build. Next, we will use a work manager instance to perform

this task. WorkManager.getInstance

We need to pass the context here. Then Enque oneTimeWorkRequest.

Let’s rename this as uploadRequest. That’s much better.

Name is not a problem. You can add any name you like.

Now, I am going to add a button and write codes to trigger this task inside the

click listener

of it. Go to activity_main.xml file. Switch to design mode. Drag a button. Set constraints.

Let’s change the text on the button.

Change the text size and text style also.

We will use this textview later. So, let’s modify it too. I am removing the text on it.

Change the text size to 30 and text style to bold.

Ok. Now Let’s go back to main activity. Then, in this onCreate function, I am implementing the click listener

of the button.

Let’s call to the setOneTimeWorkRequest() function from here. Ok, Let’s run the app and see how this works.

Let’s open the logcat and see the results.*/



-----------------------Get status updates from Workers----------------------

For each work request , work manager allows us to get a LiveData of type WorkInfo .

WorkInfo object contains information about the work. By observing it we can determine the current status and

other information about the work.

Let me show you how to do it. To observe live data,

we need the work manager instance .

We have already use the work manager here.

So instead of invoking getInstance twice, let’s define this as a separate variable. val workManager

equals WorkManager.getInstance

Pass the applicationContext.

Now, replace this part with our new instance.

All right,

Then I am going to write codes to observe the WorkInfo. workManager

.getWorkInfoByIdLiveData here,

we need to provide the id of the work request. uploadRequest.id then observe

owner is this activity. Observer.

Here, you can see we have a WorkInfo object as it.

Then, we will display the current state of the work on the textview.

textView.text = it. You can see, various information we can get from a workInfo object. state.name

Now, let’s run the app and see how this works.

We can see the state as Succeeded.

Actually a work has four states. BLOCKED, ENQUEUED, RUNNING and SUCCEEDED. Blocked state occurs only if

the work is blocked in a chain of works.

Work enters to ENQUEUED state as soon as the work is next in the chain of work and eligible to run.

IF there is a constraint to be met, work will have to

stay in the enqueued state. When a work is actively executing it is called

running state. After a work successfully completed, work enters to succeeded state. Actually, we

will be able to see the running state

if we increase the value in this for loop. Let’s make it 600000.

Run the app again to see how this works.

You can see the running state now.

And finally work entered to succeeded state.

So, this is how we get state updates form a work.

