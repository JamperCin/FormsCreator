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
	        implementation 'com.github.JamperCin:formscreator:1.0.8'
	}



  
  **Step 3: Just call it from your activity or fragment like this:**
  ```
  new EngineBean(getActivity(), getFragmentManager())
                .setOnSubmitClickListener(new OnSubmitOnClick() {
                    @Override
                    public void submit() {

                        Toast.makeText(context, "Make a call to submit all data or save data " + new FormsUtils(getActivity()).getAnswer("A01"), Toast.LENGTH_SHORT).show();
                    }
                })
                .Builder("forms.json", viewPager);
```

 **: If you have a textView which will display a text as a header, you can pass the textView before calling your Builder():**
  ```
  new EngineBean(getActivity(), getFragmentManager())
                .setHeaderView(tvPageHeader)
                .setOnSubmitClickListener(new OnSubmitOnClick() {
                    @Override
                    public void submit() {

                        Toast.makeText(context, "Make a call to submit all data or save data " + new  FormsUtils(getActivity()).getAnswer("A01"), Toast.LENGTH_SHORT).show();
                    }
                })
                .Builder("forms.json", viewPager);

```

**: Sample forms should follow this format in order to be renderred :**
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
					"type":"textViewBold",
					"required" : "true"
				},
				{
					"label":"",
					"code":"A01",
					"type":"textarea",
					"inputType":"0",
					"required" : "true"
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
					"label":"HH Contact Phone Number 2",
					"code":"A03b",
					"type":"text",
					"required":"false",
					"inputType":"1"
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
					"type":"textViewBold",
					"required" : "true"
				},
				{
					"label":"Detail Address of Structure",
					"code":"A04",
					"type":"textarea",
					"required" : "true"
				},
				{
					"label":"Structure number of house/compound",
					"code":"A05",
					"type":"text",
					"required" : "true"
				},
				{
					"label":"Serial Number of household within house/compound",
					"code":"A06",
					"type":"text",
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
					"type":"buttonControls"
				}
			]
		}
           ]
}

```
