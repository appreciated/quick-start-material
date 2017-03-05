# quick-start-material [![](https://jitpack.io/v/appreciated/quick-start-material.svg)](https://jitpack.io/#appreciated/quick-start-material)
A Quick-Start Material implementation for Vaadin

## Status
This Library is incomplete! 
I plan to reuse as much CSS from Materialize but havent had the time yet (https://github.com/dogfalo/materialize/). 
The Library is usable but there are no tests yet. 

## How do I use it?

This Library will be added as a Vaadin Addon when a more mature status is reached, for now I decided to use JitPack

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
      <version>0.5.6</version>
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

### Finally replace valo with the quick-start-material theme 
  
    @import "../quick-start-material/quick-start-material.scss";

    @mixin mytheme {
      @include quick-start-material;
    }

  
### (Optional) If you don't need theme customaziations you can replace valo in the UI class

    @Theme("quick-start-material")
    public class MyUI extends WebApplicationUI {
       ...
