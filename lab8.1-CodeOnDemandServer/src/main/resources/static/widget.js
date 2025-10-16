(function() {
    const widget = document.createElement('div');
    widget.style.padding = '15px';
    widget.style.background = '#e0ffe0';
    widget.style.border = '1px solid #00aa00';
    widget.style.marginTop = '20px';
    widget.style.fontFamily = 'Arial, sans-serif';
    widget.innerText = '🌿 Widget loaded dynamically from Spring Boot Server!';
    document.body.appendChild(widget);
})();
