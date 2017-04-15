window.com_github_appreciated_quickstart_material_component_desktop_DesktopMenuBarAnimator = function () {
    this.onStateChange = function () {
        animateScroll();
    }

    function animateScroll() {
        console.log('desktop says hello from javascript!');
        var element = document.getElementById('contentPanel');
        var childElement = element.getElementsByClassName('v-panel-content').item(0);
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
        });
    }
};