﻿<a name="br1"></a> 

Meals Me App

Karam Abd | Android Programming | 12-04-2020



<a name="br2"></a> 

Contents

[The](#br3)[ ](#br3)[purpose](#br3)[ ](#br3)[Meals](#br3)[ ](#br3)[Me](#br3)[ ](#br3)[App:.....................................................................................................2](#br3)

[The](#br3)[ ](#br3)[target](#br3)[ ](#br3)[users](#br3)[ ](#br3)[for](#br3)[ ](#br3)[Meals-Me](#br3)[ ](#br3)[App:.....................................................................................2](#br3)

[The](#br3)[ ](#br3)[Functionality](#br3)[ ](#br3)[of](#br3)[ ](#br3)[The](#br3)[ ](#br3)[App:..............................................................................................2](#br3)

[Technical](#br4)[ ](#br4)[design:](#br4)[ ](#br4)[......................................................................................................................3](#br4)

[Class](#br4)[ ](#br4)[Diagram:.......................................................................................................................3](#br4)

[Wireframing](#br6)[ ](#br6)[description:](#br6)[ ](#br6)[.....................................................................................................5](#br6)

[Functional](#br7)[ ](#br7)[design:.....................................................................................................................6](#br7)

[User](#br9)[ ](#br9)[Manual:.............................................................................................................................8](#br9)

[Conclusion:..........................................................................................................................](#br15)[ ](#br15)[14](#br15)

PAGE 1



<a name="br3"></a> 

The purpose Meals Me App:

Meals me app idea is to help you make use of any cooking item of vegetables, oil, spices and

seeds to advice you with a meal based on what you have at home.

THE TARGET USERS FOR MEALS-ME APP:

\-

\-

\-

People with few knowledges about cooking.

People who suffer from poverty and have limited access to specific items.

People who allergic from items they can find meals by deselecting items.

THE FUNCTIONALITY OF THE APP:

The user will select – deselect items existed at his/her place to get back a result of meals can

be served or cooked with these items. It offers also other functionalities:

\-

\-

\-

\-

\-

\-

\-

Supports both Arabic and English languages (English is the default language).

Sorting by item type (Vegetables, Fruits, Seeds, Oils and Milk types).

Sorting by cold/warm meals.

Sorting by Duration, Calories, and Name.

Updating a meal

Search for items

Recommendation for meals based on what you select of items.

PAGE 2



<a name="br4"></a> 

Technical design:

CLASS DIAGRAM:

**User**

**Meal**

username: String

meals: ArrayList<Meal>

recommendedMeals: ArrayList<Meal>

recentMeals: ArrayList<Meal>

items: ArrayList<Item>

currentSelectedMeal: Meal

language: String

User(String)

setUsername(String):void

getRecentMeals(): ArrayList<Meal>

addToRecentMeals(Meal): void

setCurrentSelectedMeal(Meal): void

getCurrentSelectedMeal(): Meal

setLanguage(String): void

getLanguage(): String

getUsername(): String

getItems(): ArrayList<Item>

updateItems(): void

getAllMeals(): ArrayList<Meal>

checkIfNameExisted(Meal): boolean

checkIfNameExistedStr(String): boolean

addMeal(Meal):Boolean

mealName: String

items: ArrayList<Item>

description: String

image: int

durationInMinutes: int

isSelected: boolean

dateCreated: Date

Meal(String, String, int, int)

Meal(String, String, int)

Meal()

setMealName(String): void

setDescription(String): void

setItems(ArrayList<Item>): void

setDurationInMinutes(int): void

addItem(Item): void

getMealName(): String

getDescription(): String

getDurationInMinutes(): int

getItems(): ArrayList<Item>

setImage(int): void

getImage(): int

getMealByName(String): Meal

getItemByName(String): Item

getAllRecommendedMeals(): void

getRecommendedMeals(): ArrayList<Meal>

filterRecommendedMeals(String): void

getSelectedItems(): ArrayList<Item>

getUnSelectedItems(): ArrayList<Item>

setAllItemsUnSelected(): void

setAllItemsSelected(): void

getDateCreated(): Date

setDateCreated(Date): void

MealNameComparator(): Comparator<Meal>

MealCaloriesComparator(): Comparator<Meal>

MealDurationComparator(): Comparator<Meal>

selectItemByName(String): void

PAGE 3



<a name="br5"></a> 

**Item**

itemName: String

description: String

calories: int

picture: int

isVegan: boolean

isVegetarian: boolean

isCarnicore: boolean

isSelected: boolean

Item(String, String, int, int, int, boolean, boolean,

boolean)

Item(String, String, int, int, int, boolean, boolean,

boolean, boolean)

getDescription(): String

isVegan(): boolean

isVegetarian(): boolean

isCarnivore(): boolean

getItemName(): String

getPicture(): int

getCalories(): int

isSelectedChange(): void

isSelected(): boolean

unSelectedChange(): void

selectedChange(): void

compareTo(Item): int

PAGE 4



<a name="br6"></a> 

WIREFRAMING DESCRIPTION:

The idea of the app came to me from a daily issue I face everyday, I start thinking simulating

how I thing when I want to cook something, I start thinking what I have already at home of

items, them my brain thinks of meals I already know, but then I thought of a question, what

if I have a dictionary of all meals in my brain, would it be a problem for me to prepare meals

even with small number of items, I then I thought of applying this idea into an app, and I

was also interviewing my friends about the idea and how would it help them, they were

happy about having such an app in their phone, specially those who left their homes to live

alone. I start designing the app process exactly how I processes it in my brain. First page

that holds the preferred meals (or recently visited meals) in our case, then a screen to collect

items, then a screen to show the recommended meals, I also thought of having a sort, so

people who prefer to have meals with less duration, calories, and for those who would love

to see seek meals they have heard about it. Then a screen to show the details of the meal to

be prepared. I also thought about editing meals based on the user preference, people may

remove items such us salt, sugar or any thing else. Or even update the name of the meal,

and the description. I used a virtual machine “Pixel 2 API 29” and my cell phone “Samsung

S10 plus”.

PAGE 5



<a name="br7"></a> 

Functional design:

Meals

ItemListView

PAGE 6



<a name="br8"></a> 

**ttingsActivity**

**AddMeal**

PAGE 7



<a name="br9"></a> 

User Manual:

1- Main Application Screen:

This screen will appear to you once you click on the icon of the

app in the apps list in your phone, it contains a button to start

looking for a meal, and a list contains the recent visited meals. It

also contains a menu in the nav bar of the screen in the up-right

corner that will take you to the application settings. See picture

(1).

PAGE 8



<a name="br10"></a> 

After pressing the button “FIND NEW MEAL”, you will find yourself in the screen where

you start selecting the items you have, and you can filter those items as written in picture

(2), also you can search for an item by start typing the item name, with every letter you

write, you will get on real time results. The pictures (3,4) are showing the results when you

select to filter between items. The custom drawing in the bottom of the screen shows the

selected items progress, in simple English: how many items you’ve selected compared of

the over all items.

PAGE 9



<a name="br11"></a> 

After selecting all items that you want to use for preparing your meal, You

will get a list of the meals recommended for you to choose from, where

these meals will have the items you selected, or at least part of it. In picture

(5) you see that the screen has the functionality to sort the recommended

meals based on its Duration as picture (6), name as picture (7), and

calories as picture (8).

PAGE 10



<a name="br12"></a> 

After choosing the meal you’re looking

for, you will get a screen with details

about how to prepare it, with scroll

down function, you’ll be able to read

the all text even if it is too long. Please

look at picture (9)

PAGE 11



<a name="br13"></a> 

when you press the menu button in the top-right of the screen,

you will be able to open the settings screen, where you will find

it as the picture (10) with buttons to choose from, it contains a

rating scroll down spinner that can help you rate the app see

picture (11), also you can clear the database, and you can choose

to let all the items to be

selected by default. You

will be able to select

another language as well,

the app supports both

(English and Arabic)

languages, see picture

(12).

PAGE 12



<a name="br14"></a> 

When selecting the button “ADD NEW MEAL” in picture (10) in settings, you will be

forward to update or add a meal, as you see in picture (13), by selecting the meal from the

list, you can update the name, the description, the duration see picture (14), and also the

items see picture (15). You can always press the button “Back” in your cell phone to get

back to the previous screen.

PAGE 13



<a name="br15"></a> 

Conclusion:

The application will be an ideal idea for making use of all items that people don’t know

how to use it when it come to cooking, it gives another option not to through it away if it

was extra not to know what to do with it, the data set in the application was made for

viewing only, it needs a huge online data set which will be the next target.

PAGE 14


