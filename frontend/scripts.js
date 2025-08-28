(function () {
    const endpoints = {
        newGame: 'http://localhost:8080/new-game',
        ask: 'http://localhost:8080/ask'
    };

    const messagesEl = document.getElementById('messages');
    const sendBtn = document.getElementById('sendBtn');
    const textInput = document.getElementById('textInput');
    const promptsLeftEl = document.getElementById('promptsLeft');
    const remainingEl = document.getElementById('remaining');
    const newGameBtn = document.getElementById('newGameBtn');
    const gameHint = document.getElementById('gameHint');

    let game = null; // {word, prompts, guessed}
    let bot = 'Bot Bartek';

    function safeText(s) {
        return String(s || '').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    }

    function addMessage(text, who = bot, meta = '') {
        const row = document.createElement('div');
        row.className = 'row ' + (who === 'me' ? 'me' : '');
        const bubble = document.createElement('div');
        bubble.className = 'msg ' + (who === 'me' ? 'me' : bot);
        bubble.innerHTML = '<div>' + safeText(text) + '</div>' + (meta ? ('<div class="timestamp">' + safeText(meta) + '</div>') : '');
        row.appendChild(bubble);
        messagesEl.appendChild(row);
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }

    function setStatus() {
        if (!game) {
            promptsLeftEl.textContent = 'Brak gry';
            remainingEl.textContent = '--';
            return;
        }
        promptsLeftEl.textContent = game.prompts + ' pytań zadanych';
        remainingEl.textContent = game.prompts;
        gameHint.textContent = game.guessed ? 'Słowo odgadnięte: ' + '[ukryte]' : '';
        if (game.guessed) {
            textInput.disabled = true;
            sendBtn.disabled = true;
            textInput.classList.add('disabled');
            sendBtn.classList.add('disabled');
        } else {
            textInput.disabled = false;
            sendBtn.disabled = false;
            textInput.classList.remove('disabled');
            sendBtn.classList.remove('disabled');
        }
    }

    async function startNewGame() {
        try {
            setControlsLoading(true);
            const res = await fetch(endpoints.newGame, {method: 'GET'});
            if (!res.ok) throw new Error('Błąd serwera: ' + res.status);
            const payload = await res.json();
            game = {word: payload.word, prompts: Number(payload.prompts) || 0, guessed: Boolean(payload.guessed)};
            messagesEl.innerHTML = '';
            addMessage('Witaj! Zaczynamy nową grę. Zadaj pierwsze pytanie lub spróbuj zgadnąć.', bot);
            setStatus();
        } catch (err) {
            console.error(err);
            addMessage('Nie udało się rozpocząć gry: ' + err.message, bot);
        } finally {
            setControlsLoading(false);
        }
    }

    function setControlsLoading(loading) {
        if (loading) {
            newGameBtn.textContent = 'Ładowanie...';
            newGameBtn.disabled = true;
        } else {
            newGameBtn.textContent = 'Nowa gra';
            newGameBtn.disabled = false;
        }
    }

    async function sendQuestion() {
        const text = textInput.value.trim();
        if (!text) return;
        if (!game) {
            addMessage('Brak aktywnej gry. Kliknij "Nowa gra".', bot);
            return;
        }
        addMessage(text, 'me');
        const payload = {message: text, game: {word: game.word, prompts: game.prompts, guessed: game.guessed}};
        textInput.value = '';
        textInput.focus();

        try {
            sendBtn.disabled = true;
            textInput.disabled = true;
            const typingEl = document.createElement('div');
            typingEl.className = 'row';
            typingEl.id = 'typing';
            typingEl.innerHTML = '<div class="msg bot">...<div class="timestamp">Bot</div></div>';
            messagesEl.appendChild(typingEl);
            messagesEl.scrollTop = messagesEl.scrollHeight;

            const res = await fetch(endpoints.ask, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(payload)
            });
            if (!res.ok) throw new Error('Błąd zapytania: ' + res.status);
            const reply = await res.json();
            const tp = document.getElementById('typing');
            if (tp) tp.remove();

            game.prompts = Number(reply.promptsUsed) || game.prompts;
            addMessage(reply.message, bot);

            if (reply.won) {
                game.guessed = true;
                addMessage('🎉 Gratulacje! Odgadłeś słowo!', bot);
                addMessage('Słowo (ukryte od serwera) zostało odgadnięte.', bot);
            }

            setStatus();

        } catch (err) {
            console.error(err);
            const tp = document.getElementById('typing');
            if (tp) tp.remove();
            addMessage('Błąd komunikacji: ' + err.message, bot);
        } finally {
            sendBtn.disabled = false;
            textInput.disabled = false;
        }
    }

    sendBtn.addEventListener('click', sendQuestion);
    textInput.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') sendQuestion();
    });
    newGameBtn.addEventListener('click', startNewGame);

    startNewGame();
    window.__game = () => ({game});
})();