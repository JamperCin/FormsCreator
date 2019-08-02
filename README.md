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
	        implementation 'com.github.JamperCin:formscreator:1.0.3'
	 }


  
  
  **Step 3: Just call it from your activity or fragment like this:**
  ```
  new EngineBean(getActivity(), getFragmentManager()).Builder("forms.json", viewPager);
```

 **Step 3: Just call it from your activity or fragment like this:**
  ```
  new EngineBean(getActivity(), getFragmentManager()).setHeaderView(tvPageHeader).Builder("forms.json", viewPager);

```
