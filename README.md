# quick-start-material [![](https://jitpack.io/v/appreciated/quick-start-material.svg)](https://jitpack.io/#appreciated/quick-start-material)
A Quick-Start Material implementation for Vaadin

## Status
This Library is incomplete most Components require still a manual sdf change. I planned to reuse as much from the Materialize but havent had the time yet (https://github.com/dogfalo/materialize/). The Library is usable but there are no tests yet. 

## How to Use?


### Add the JitPack Repository to your POM

    <repositories>
	    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
	    </repository>
     </repositories>

### Add the following POM to your Maven dependencies
  
    <dependency>
      <groupId>com.github.appreciated</groupId>
      <artifactId>quick-start-material</artifactId>
      <version>0.5.5</version>
    </dependency>
  
### Modify your UI Class to look similar to this
  
    public class MyUI extends WebApplicationUI {

      @Override
      public WebAppDescription initWebAppDescription() {
          return new WebAppDescription()
                .withDefaultDesign(MaterialDesktopView.class) // Set the "design" for the desktop
                .withMobileDesign(MaterialMobileView.class) // Set the "design" for the mobile platform
                .withLoginDesign(LoginPageView.class) // Set a login page if you want to have one
                .withTitle("TestWebsite")
                .withNavigation(new TestView1()) // add all the Views your want to be able to navigate in
                .withNavigation(new TestView2()) // ...
                .withDefaultPage(TestView1.class); // Set the default page
      }

      @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
      @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
      public static class MyUIServlet extends VaadinServlet {
      }
      
    }

  
