package com.elfilibustero.quizboard;

import com.elfilibustero.quizboard.beans.QuizBean;

import java.util.ArrayList;

public class QuizList {
	public static ArrayList<QuizBean> quizBean;

	public static ArrayList<QuizBean> getQuizList() {
		if (quizBean == null) {
			quizBean = new ArrayList<>();
		}

		quizBean.add(new QuizBean("Gyroscope component is supported on every device.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("The index of the first item in the list is 1.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Saved SharedPreference data remains even if you delete the application.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Saved SharedPreference data will delete after you restart the application.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can only access the events from the \"Event\" tab.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can perform and cancel multiple Timer tasks using a single Timer component.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can use the Firebase Library just by adding it in the \"Component\" tab.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("It's possible to place multiple widgets inside the \"Scroll\" Layout Widget.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("SoundPool component can play any sound file of any length.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Adding a Drawer or FAB in an activity has no effect on the compilation time.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("The activity's lifecycle, \"onStop\" occurs after the \"onDestroy\" event.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("Deleting the BlackLogics app will remove all the saved projects.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("BlackLogics automatically saves the project for you.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("SharedPreferences and FileUtil does the same thing.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can update the app on the Play Store using a different release key.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("A local project that has not been backed up can be recovered if deleted.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("There can be duplicate ID for widgets.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("You can only disable widgets through its \"enabled\" property.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("The \"alpha\" property creates a shadow behind the widget.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("The \"List\" can only store a list of numbers.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("You can only check for one condition inside the if-else block.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("Intent component's \"CLEAR_TOP\" removes the uppermost screen.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("The \"finishActivity\" block is used to close the application.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("It's not possible to programmatically scroll the ListView using blocks.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("It's not possible to open other applications using the Intent component.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("ObjectAnimator component is the only way to animate Widgets.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("After you add a component, its associated events are automatically added.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("My Collection of \"Project A\" can not be accessed from \"Project B.\"",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("It's possible to upload the APK file to the Play Store without signing.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can delete \"My Collection\" items from the Image, Sound, and Font managers.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Setting a fixed dp on the width and height of a widget is usually recommended.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can not use another Layout widget inside a Layout.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("You can not set the max number of lines a TextView can display.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can not disable the EditText widget.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can not redirect the user to a website using blocks.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can divide a number by 0.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can not copy blocks to other activity.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Calendar and CalendarView are the same thing.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can only add one component per activity.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Variable names \"Abc\" and \"abc\" are the same variables.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can not add duplicate items in the list.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can UNDO the action of creating a variable.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can create a widget with a duplicate ID.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can lower the version code of your application.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("Lifecycle event, onCreate, can be deleted.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("A deleted project can be recovered.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("A deleted Collection item can be recovered.", QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("A variable created in \"Activity A\" can be accessed from \"Activity B.\"",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("A component created in \"Activity A\" can be accessed from \"Activity B.\"",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(
				new QuizBean("You can use custom Blocks created with BlackLogics in BlackLogics from Google Play.",
						QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean(
				"File blocks work on Android 11 and newer when target SDK version is set to 30 and higher.",
				QuizBean.QUIZ_TYPE_OX));
		quizBean.add(new QuizBean("You can choose a file using \"FilePicker\" component.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("The \"finishAffinity\" block is used to close the application.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can add tags to AndroidManifest in the AndroidManifest manager.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can create your own components in the Component manager.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can back up a single project, with all used custom blocks and libraries.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can add a Drawer in the View Manager.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can set both \"image\" and \"background resource\" on an ImageView.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can easily add events inside the property tab using \"Event\" tab.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can change the color of an ImageView using \"setColorFilter\" block.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Lifecycle event, onCreate, is the first thing that is called when the app starts.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Trying to access an invalid key from Shared Preference can cause the app to crash.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can upload and download files using Firebase Storage", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("To assign values to list items in ListView, you need to add \"onBindView\" event.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can resize pictures on your device using FileUtil blocks.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can change the default color of status bar in the project's advanced settings.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can create a login authentication system using Firebase Auth.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can overlap widgets using translation and rotation properties.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"To use the Firebase library, you have to first set up the configuration in the Library Settings.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("ImageView's \"ScaleType\" property determines the ratio of the image being displayed.",
						QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can create a custom list item for the ListView.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Using forever block without the stop block will hang and crash the application.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Blocks inside the if-then block will only execute if the condition is true.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can store different types of values using \"Variables.\"", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Intent component can be used to transfer data between activities.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("It's possible to create a custom item for the ListView.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("My collection is a shared collection of data for all the projects.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("It's possible to save widgets and blocks for later use.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("New component blocks are added when you add a component.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("MoreBlock is a purple block you can create for repetitive tasks.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can programmatically change the font style.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("There is a Discord community that can help you.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can add onClick event on Widgets other than the Button.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can also add events inside the \"Component\" tab.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can change the language settings for BlackLogics.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can edit the image file after it has been added.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can export the project to continue working on Android Studio.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can sign the project to upload it to the Play Store.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You need a Play Store Developer account to upload your APK file to the Play Store.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can center the text using the \"gravity\" property.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can view the translated Java and XML source file.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can upload pictures to Firebase Storage.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"In order to swap the value of two variables, you need a third variable for a placeholder.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"Weight property is used when you want to make your widget size responsive to the screen size.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"Trim block in the Operator tab trims the extra white spaces in the beginning and the end.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("It's possible to change a number to String, and vice versa.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can calculate the width of the screen using blocks.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"You can detect collisions of different widgets using \"getLocationX\" and \"getLocationY\" blocks.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"Intent component's \"CLEAR_TOP\" flag removes all the previous activities in the navigation stack.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"Intent component's \"SINGLE_TOP\" flag re-uses the activity rather than creating a new one if it exists in the navigation stack.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("The \"startActivity\" block is used to navigate to another activity.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("In order to play sound using SoundPool, you must assign a max stream count first.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can animate a Widget using the ObjectAnimator component.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Some components come with its own events that you can manually add.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can create a pop-up message using the Dialog component.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Timer component may not cancel if you assign more than one task to it.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can use MoreBlocks to develop more quickly and efficiently.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can share finished projects to other people without BlackLogics.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Verbally abusing other people in the community could result in a ban.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can use a MoreBlock within a MoreBlock.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("It's possible to add custom hex color code.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"You can set an additional set of images on an ImageView using the \"background resource\" property.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"The events in the \"Drawer\" category appear when you add a widget on the \"drawer.xml\" file.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("Google's Firebase service is free to use for small applications.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("The Camera component can be used to take pictures.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"You can create your own validator for the EditText by editing the \"onTextChanged\" event.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("You can programmatically change the styling of the widgets using blocks.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"The \"Hint\" property for EditText hints the user for what should be accepted as an input.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"The \"INVISIBLE\" option for visibility property makes the widget disappear, but still takes up space.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("It's possible to create a landscape application on BlackLogics.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("It's possible to infinitely loop a sound file.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"You can create algorithms with blocks, such as an algorithm that shuffles a deck of card.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean(
				"The \"onCreate\" event is the first lifecycle event that occurs when an activity is started.",
				QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(
				new QuizBean("You can not get an item from the list using an index greater than the size of the list.",
						QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("The items in the \"List\" are ordered by the time save.", QuizBean.QUIZ_TYPE_INFO));
		quizBean.add(new QuizBean("This component is used to animate Widgets.", QuizBean.QUIZ_TYPE_OX, "MediaPlayer",
				"ObjectAnimator"));
		quizBean.add(new QuizBean("This component is used to play longer sound files.", QuizBean.QUIZ_TYPE_OX,
				"SoundPool", "MediaPlayer"));
		quizBean.add(new QuizBean("This width / height property value fills up the parent's entire space given.",
				QuizBean.QUIZ_TYPE_OX, "WRAP_CONTENT", "MATCH_PARENT"));
		quizBean.add(new QuizBean("This property lets you position the children inside the layout.",
				QuizBean.QUIZ_TYPE_OX, "layout gravity", "gravity"));
		quizBean.add(new QuizBean("This property lets you add spacing around the widget.", QuizBean.QUIZ_TYPE_INFO,
				"padding", "margin"));
		quizBean.add(new QuizBean("How do you change the transparency of the background?", QuizBean.QUIZ_TYPE_INFO,
				"alpha", "background color"));
		quizBean.add(new QuizBean("How do you set a number keyboard type for EditText?", QuizBean.QUIZ_TYPE_OX, "ime",
				"numberDecimal"));
		quizBean.add(new QuizBean("Which lifecycle event gets called when an activity is restarted?",
				QuizBean.QUIZ_TYPE_INFO, "onResume", "onStop"));
		quizBean.add(new QuizBean(
				"Which property gets applied if you apply both \"singleLine=true\" and \"lines=3\" property on EditText?",
				QuizBean.QUIZ_TYPE_INFO, "singleLine", "lines=3"));

		return quizBean;
	}
}
