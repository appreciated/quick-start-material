[![](https://jitpack.io/v/appreciated/quick-start-material.svg)](https://jitpack.io/#appreciated/quick-start-material) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ebd859c2020b430790380477ab0551ef)](https://www.codacy.com/app/appreciated/quick-start-material?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=appreciated/quick-start-material&amp;utm_campaign=Badge_Grade) 
[![Coverage Status](https://coveralls.io/repos/github/appreciated/quick-start-material/badge.svg?branch=master)](https://coveralls.io/github/appreciated/quick-start-material?branch=master)

# quick-start-material 
A Quick-Start Material implementation for Vaadin

## Status
This Project is still under construction! You can already use it for Experiments but be ready for extensive API changes even in minor versions. 
It will be added as a Vaadin Addon when a more mature status is reached, for now I decided to use JitPack

ToDo:
* Debugging 
* Materialize import
* SCSS cleanup
* Integration Tests
* easy access management integration

### How do I use it?

#### 1. Add the JitPack Repository to your POM

    <repositories>
      <repository>
         <id>jitpack.io</id>
         <url>https://jitpack.io</url>
      </repository>
    </repositories>

#### 2. Add the following dependancy to your POM
  
    <dependency>
        <groupId>com.github.appreciated</groupId>
        <artifactId>quick-start-material</artifactId>
        <version>LATEST</version>
    </dependency>
  
#### 3. Modify your UI Class and add your Component (Components must be a decendant of the Interface "Subpage"

    @Theme("....")
    @SplashScreenConfigurator(value = CustomSplashScreenConfigurator.class)
    public class MyUI extends MaterialUI {

        @Override
        public WebAppDescription initWebAppDescription() {
            return new WebAppDescriptionBuilder().withDesign(MaterialDesktopView.class) // Set the "design" for the desktop platform
            .withMobileDesign(MaterialMobileView.class) // Set the "design" for the mobile platform
            .withLogin(new ExampleAccess()) // Set your custom login procedure
            .withRegistration(new ExampleRegistration()) // Set your custom registration procedure
            .withLoginPage(MaterialLogin.class) // Set a login Subpage if you want to have one, otherwise a login / registration button will be shown on the menu
            .withTitle("TestWebsite")
            .withSubpages(new HomeView(),     // add all the Views your want to be able to navigate in
                    new HistoryView(),        // ...
                    new PagerView())          // ...
            .withDefaultPage(TestView1.class) // Set optional the default shown Subpage
            .build();
        }

        @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
        @VaadinServletConfiguration(com.github.appreciated.quickstart.material.ui = com.github.appreciated.appreciated.MyUI.class, productionMode = false)
        public static class MyUIServlet extends VaadinServlet {
        }

    }

#### 4. Replace valo with the quick-start-material theme 
    // For the material design color palette take a look here https://www.materialpalette.com/
    $theme-primary-color: #607D8B;  // F.e (Blue Grey) Primary color for Background
    $theme-accent-color: #03A9F4; // F.e (Light Blue) Accent color for Components

    @import "../quick-start-material/quick-start-material.scss";

    @mixin mytheme {
        @include quick-start-material;
    }

#### 5. (Optional) If you are working with the Vaadin Designer 
The Designer won't be able to successfully compile the SCSS because it does not have access to it. To fix this you will either need to copy the theme manually to src/main/webapp/VAADIN/themes or is you could use a maven plugin for that job (which I recommend). The theme will updated automatically when using the plugin.  
You might then also want to add `src/main/webapp/VAADIN/themes/quick-start-material/` to your .gitignore

    <plugin>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
            <execution>
                <id>unpack-one</id>
                <phase>generate-resources</phase>
                <goals>
                    <goal>unpack-dependencies</goal>
                </goals>
                <configuration>
                    <includeGroupIds>com.github.appreciated</includeGroupIds>
                    <includeArtifactIds>quick-start-material</includeArtifactIds>
                    <type>zip</type>
                    <excludes>*</excludes>
                    <includes>VAADIN/themes/**</includes>
                    <outputDirectory>src/main/webapp/</outputDirectory>
                </configuration>
            </execution>
        </executions>
    </plugin>
