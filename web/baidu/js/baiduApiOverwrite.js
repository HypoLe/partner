/********
**huhao
**重写百度地图API,不需要看懂，你懂的！
**2012-09
*********/
if (typeof(HTMLElement) != "undefined" && !window.opera
            && HTMLElement.prototype
            && !HTMLElement.prototype.insertAdjacentHTML) {
        HTMLElement.prototype.insertAdjacentHTML = function(i, aX) {
            var aY = this.ownerDocument.createRange();
            aY.setStartBefore(this);
            aY = aY.createContextualFragment(aX);
            switch (i.toLowerCase()) {
                case "beforebegin" :
                    this.parentNode.insertBefore(aY, this);
                    break;
                case "afterbegin" :
                    this.insertBefore(aY, this.firstChild);
                    break;
                case "beforeend" :
                    this.appendChild(aY);
                    break;
                case "afterend" :
                    if (!this.nextSibling) {
                        this.parentNode.appendChild(aY)
                    } else {
                        this.parentNode.insertBefore(aY, this.nextSibling)
                    }
                    break;
            }
        };
    }