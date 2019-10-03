# FormsCreator
This project is to easily use json to create forms for quick onboarding  used by most developers.
[![](https://jitpack.io/v/JamperCin/FormsCreator.svg)](https://jitpack.io/#JamperCin/FormsCreator)

**Step 1: Add this to your root build.gradle(Project level) at the end of repositories:**

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  **Step 2: Add the dependency to your dependencies in the build.gradle (Module:App level)**
  

        dependencies {
	        implementation 'com.github.JamperCin:formscreator:1.1.1'
	}


**Step 3:Add the custom viewpager to your xml layout:**
```
 <com.kode.formscreatorlib.Utils.CustomViewPager
            android:id="@+id/viewPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_below="@+id/tv_page_header"
            android:layout_alignParentStart="true"
            android:layout_alignParentLeft="true"/>

```

  
  **Step 3: Initialiase your viewPager and create the Forms by instantiating the EngineBean() class just like below:**
  ```
 CustomViewPager viewPager = view.findViewById(R.id.viewPager)

 new EngineBean(getActivity(), getFragmentManager()).Builder("forms.json", viewPager);
```

 **If you have a textView which will display a text as a header, you can pass the textView before calling your Builder()**
 In this case, in the json, define a **title** in the form header section.
  ```
   CustomViewPager viewPager = view.findViewById(R.id.viewPager); 
   TextView tvPageHeader = view.findViewById(R.id.header);
   
   new EngineBean(getActivity(), getFragmentManager())
                .setHeaderView(tvPageHeader)
                .Builder("forms.json", viewPager);

```

**NB The format of json should be in this format in order to be rendered**
Every json should have a top most structure  like below :

 **form :** Which is the name of the form to be rendered, (OPTIONAL)
 
 **title :** Which is the title of the form to be shown on top of all the forms or on the toolbar,as defined above (OPTIONAL)
 
 **section :** Which is the section in which this part of the json falls (OPTIONAL)
 
 **pages :** Which is the list of the pages to be rendered, This array determines the number of pages to have or flip through in the viewpager. Every page contains list of views to be rendered. (MANDATORY)
 
```
  {
	"form":"Onboarding Forms For GCB",
	"title": "Household Population Questionnaire",
	"section":"Section B: Socio-Demographic Characteristics", 
	"pages":[ ]
	
}

```

**To create your list of pages, follow the following format to get your json rendered :**

 **fieldCode :** Which is the unique Code that denotes every single page created, (MANDATORY)
 
 **isRepeat :** Which is a boolean value to indicate whether this page will be created multiple times based on a value entered by user. By default this value is FALSE (OPTIONAL)
 
 **fields :** Which is the list of the individual views created in every single page.  (MANDATORY)
 
 
```
  {
	"form":"Onboarding Forms For GCB",
	"title": "Household Population Questionnaire",
	"section":"Section B: Socio-Demographic Characteristics", 
	"pages":[
	
	  {
	   "fieldCode" : "Page_1",
	   "isRepeat":true,
	   "fields":[ ]
	  },
	  
	  {
      	"fieldCode" : "Page_2",
      	"isRepeat":false,
        "fields":[ ]
      },
      {
      	 "fieldCode" : "Page_3",
      	 "isRepeat":false,
         "fields":[ ]
      }
      
	 ]
	
}

```




**Register an onClickListener to the submit Button:**
 In this case, in the json, define a view type called **submitButton** at the part of the page where you want to submit your values or save data. 
Sample json definition of submit button : 
```
{
 "label":"Save",
 "code":"A11b",
 "type":"submitButton"
}

```

Register the onclicklister before calling Builder() method
```

   CustomViewPager viewPager = view.findViewById(R.id.viewPager)
   new EngineBean(getActivity(), getFragmentManager())
                .setHeaderView(tvPageHeader)
                .setOnSubmitClickListener(new OnSubmitOnClick() {
                    @Override
                    public void submit() {
                       
                    }
                })
                .Builder("forms.json", viewPager);

```

**: You can obtain the value/answer saved  for each field by doing the following **

Pass the unique code of a field to get the answer/value saved for particular code
Sample value for getting data saved for field 'A01'
```
 Forms f  = new FormsUtils(getActivity()).getSavedAnswer("A01");
 
  String questionCode = f.getQuestionCode();
  String question = f.getQuestion();
  String answer = f.getAnswer();
  String pageCode = f.getPageCode();
  String formCode = f.getFormCode();
```
You can get all the fields and their answers too by calling :
```
 ArrayList<Forms> forms = new FormsUtils(getActivity()).getSavedAnswer();

for(Forms f : forms){
String questionCode = f.getQuestionCode();
  String question = f.getQuestion();
  String answer = f.getAnswer();
  String pageCode = f.getPageCode();
  String formCode = f.getFormCode();
}
```


**Sample forms should follow this format in order to be renderred :**
Note : Every field should have a unique code and every page should have a unique code as well

The various supported views for now that can be rendered include:
```
**: 1. textViewBold -> :** For rendering a textview with bolded style
**: 2. textView -> :** For rendering a textview with normal style
**: 3. text -> :** For rendering an EditText for entry
**: 4. textarea -> :** For rendering a textArea field for entry
**: 5. radio -> :** For rendering a radioGroup with radio buttons specifically for a purpose where data is object and follows specifi naming patterns. (Not recommended if you dont understand)
**: 6. radioGroup -> :** For rendering a radioGroup with a list of radio buttons 
**: 7. button -> :** For rendering a button, specifically for the first Visible button to move viewpager to next screen
**: 8. pagingButtons -> :** For rendering a PREVIOUS and NEXT button, specifically for the subsequent screens to move viewpager to next screen and previous screen
**: 9. date -> :** For rendering an editext and an imagepicker to allow user to select date from a date picker
**: 10. space -> :** For rendering empty space between views, especially between a view and a button

```

Remember , for any EditText, the following inputTypes are supported: 
```
"inputType":"0", -> For normal alphabetical Text
"inputType":"1", -> For Numeric keypad
"inputType":"2", -> For Email keypad

```

```
{
	"form":"PHC1A",
	"title": "Household Population Questionnaire",
	"section":"Section B: Socio-Demographic Characteristics",
	"pages":[
		{
			"fieldCode" : "A_01",
			"fields":[
				{
					"label":"Detailed Physical Address of House/Compound",
					"code":"A00",
					"type":"textViewBold"
				},
				{
					"label":"Ghana Post Digital Address",
					"code":"A02",
					"type":"text",
					"inputType":"0",
					"required" : "true"
				},
				{
					"label":"HH Contact Phone Number 1",
					"code":"A03a",
					"type":"text",
					"inputType":"1",
					"required" : "true"
				},
		
				{
					"code":"A03c",
					"type":"space"
				},

				{
					"label":"Next",
					"code":"A03d",
					"type":"button"
				}
			]
		},
		{
			"fieldCode" : "A_02",
			"fields":[
				{
					"label":"Enumeration Area Code",
					"code":"A00",
					"type":"textView"
				},
				{
					"label":"Detail Address of Structure",
					"code":"A04",
					"type":"textarea",
					"required" : "true"
				},

				{
					"label": "Type of residence",
					"code":"A07b",
					"type":"radio",
					"required": "true",
					"options":[
						{
							"01":"Occupied housing unit",
							"02":"Vacant housing Unit"
						}
					],
					"gotoIf":[
						{
							"QuestionCode" : "A07b",
							"answer":"Vacant housing Unit",
							"next":"A_11"
						}
					]
				},
				{
					"code":"A07c",
					"type":"space"
				},

				{
					"label":"Next",
					"code":"A07d",
					"type":"pagingButtons"
				}
			]
		}
           ]
}

```
