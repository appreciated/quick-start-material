var element = document.getElementById('contentPanel'); 
                var childElement = element.getElementsByClassName('v-panel-content').item(0); 
                console.log('test'); 
                childElement.addEventListener('scroll', function () { 
                    if (document.getElementById('navigation-bar')) { 
                        if (childElement.scrollTop > 0) { 
                            if (!document.getElementById('navigation-bar').classList.contains('floating-navigation-bar')) { 
                                document.getElementById('navigation-bar').classList.add('floating-navigation-bar') 
                                console.log('added'); 
                            } 
                        } 
                        else { 
                            if (document.getElementById('navigation-bar').classList.contains('floating-navigation-bar')) { 
                                document.getElementById('navigation-bar').classList.remove('floating-navigation-bar'); 
                                console.log('removed'); 
                            } 
                        } 
                    } 
                    else { 
                        console.log('no element'); 
                    } 
                }););