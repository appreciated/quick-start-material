window.onload = function () {
    var body = document.body;
    body.addEventListener('DOMNodeInserted', OnNodeInserted, false);

    function OnNodeInserted(event) {
        var menuButtonId = 'menu-button';
        var menuId = 'menu-wrapper';
        document.getElementById(menuButtonId).onclick = function () {
            document.getElementById(menuId).classList.toggle('menu-show');
        };
        var elements = document.getElementsByClassName('mobile-tab-wrapper');
        for (var i = 0; i < elements.length; i++) {
            elements[i].addEventListener(\click\, function () {
                document.getElementById(menuId).classList.toggle('menu-show');
            });
        }
        /**
         * Close/open the menu when swiping left/right
         */
        var xDown = null;
        var yDown = null;

        function handleTouchStart(evt) {
            xDown = evt.touches[0].clientX;
            yDown = evt.touches[0].clientY;
        }

        function handleTouchMove(evt) {
            if (!xDown || !yDown) {
                return;
            }
            var xUp = evt.touches[0].clientX;
            var yUp = evt.touches[0].clientY;
            var xDiff = xDown - xUp;
            var yDiff = yDown - yUp;
            if (Math.abs(xDiff) > Math.abs(yDiff)) { /*most significant*/
                console.log(Math.abs(xDiff));
                if (xDiff > 0 && Math.abs(xDiff) >= 10) {
                    // left swipe
                    document.getElementById(menuId).classList.remove('menu-show');
                }
                else if (Math.abs(xDiff) >= 10) {
                    // right swipe
                    document.getElementById(menuId).classList.add('menu-show');
                }
            }
            else {
                if (yDiff > 0) {
                    // console.log(\up swipe\);
                }
                else {
                    // console.log(\down swipe\);
                }
            }
            /* reset values */
            xDown = null;
            yDown = null;
        }
        document.addEventListener('touchstart', handleTouchStart, false);
        document.addEventListener('touchmove', handleTouchMove, false);
    }
}