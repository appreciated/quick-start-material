# quick-start-material [![](https://jitpack.io/v/appreciated/quick-start-material.svg)](https://jitpack.io/#appreciated/quick-start-material) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/ebd859c2020b430790380477ab0551ef)](https://www.codacy.com/app/appreciated/quick-start-material?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=appreciated/quick-start-material&amp;utm_campaign=Badge_Grade)
A Quick-Start Material implementation for Vaadin

## Status
This Project is still under construction! You can already use it for Experiments but be ready for extensive API changes even in minor versions. 
It will be added as a Vaadin Addon when a more mature status is reached, for now I decided to use JitPack

ToDo:
* Debugging
* Allow Components to be added to the Navigation and the Context Menu 
* Context Buttons without List 
* Pager replacement
* Materialize import
* SCSS cleanup
* left / right side navigation
* Integration Tests

## How do I use it?

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
      <version>0.6.1</version>
    </dependency>
  
### Modify your UI Class to look similar to this
  
    public class MyUI extends WebApplicationUI {

      @Override
      public WebAppDescription initWebAppDescription() {
          return new WebAppDescription()
                .withDesign(MaterialDesktopView.class) // Set the "design" for the desktop
                .withMobileDesign(MaterialMobileView.class) // Set the "design" for the mobile platform
                .withLogin(new BasicAccess()) // Set the login procedure
                .withRegistration(new BasicRegistration())   // Set the registration procedure
                .withLoginPage(MaterialLogin.class) // Set a login subpage if you want to have one otherwise the site will be shown with a login button
                .withTitle("TestWebsite")
                .withNavigation(new TestView1()) // add all the Views your want to be able to navigate in
                .withNavigation(new TestView2()) // ...
                .withDefaultPage(TestView1.class); // Set the default subpage
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

  
### (Optional) If you don't need theme customaziations you can also replace valo in the UI class

    @Theme("quick-start-material")
    public class MyUI extends WebApplicationUI {
       ...
