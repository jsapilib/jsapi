function changeDisplay(text) {
        document.getElementById("display").innerHTML = text;
    }

    function clicked()
    {
        var states = ["Soaking", "Washing", "Rinsing", ""];
        var i = 0;
        var e = document.getElementById('spinner');
        var fn = function() {
            e.className = states[i];
            changeDisplay(states[i]);
            ++i;
			if(i > 3)
			return;
            setTimeout(fn, 3000);
        };
        fn();
    }