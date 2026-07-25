import React, { useState, useEffect, useRef } from 'react';

const ASCII_ART = `
██████╗  █████╗  ██████╗ ███████╗    ██████╗ ██╗   ██╗███╗   ██╗███████╗███████╗
██╔══██╗██╔══██╗██╔════╝ ██╔════╝    ██╔══██╗██║   ██║████╗  ██║██╔════╝██╔════╝
██████╔╝███████║██║  ███╗█████╗      ██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
██╔═══╝ ██╔══██║██║   ██║██╔══╝      ██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
██║     ██║  ██║╚██████╔╝███████╗    ██║     ╚██████╔╝██║ ╚████║███████║███████╗
╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚══════╝    ╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
`;

const INITIAL_HISTORY = [
  { type: 'art', text: ASCII_ART },
  { type: 'text', text: 'Welcome to Page Pulse Terminal! (Version 1.0.0)' },
  { type: 'text', text: "Type 'help' to see the list of available commands." },
];

function App() {
  const [history, setHistory] = useState(INITIAL_HISTORY);
  const [input, setInput] = useState('');
  const [isProcessing, setIsProcessing] = useState(false);
  const endOfTerminalRef = useRef(null);
  const inputRef = useRef(null);

  useEffect(() => {
    endOfTerminalRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [history]);

  useEffect(() => {
    // Focus input on load and click
    const focusInput = () => inputRef.current?.focus();
    window.addEventListener('click', focusInput);
    focusInput();
    return () => window.removeEventListener('click', focusInput);
  }, []);

  const addHistory = (item) => {
    setHistory((prev) => [...prev, item]);
  };

  const handleCommand = async (cmd) => {
    const trimmed = cmd.trim();
    if (!trimmed) return;

    addHistory({ type: 'command', text: `guest@pagepulse:~$ ${trimmed}` });

    const parts = trimmed.split(' ');
    const command = parts[0].toLowerCase();
    const args = parts.slice(1);

    if (command === 'clear') {
      setHistory([]);
      return;
    }

    if (command === 'help') {
      addHistory({ type: 'text', text: 'Available Commands:', className: 'text-green-400 font-bold mt-2' });
      addHistory({ type: 'text', text: '  audit <url>   - Run an SEO audit on the specified URL' });
      addHistory({ type: 'text', text: '  clear         - Clear the terminal screen' });
      addHistory({ type: 'text', text: '  help          - Show this help message' });
      return;
    }

    if (command === 'audit') {
      if (args.length === 0) {
        addHistory({ type: 'error', text: 'Error: Missing URL. Usage: audit <url>' });
        return;
      }
      
      const url = args[0];
      setIsProcessing(true);
      addHistory({ type: 'info', text: `Initiating audit for ${url}...` });

      try {
        const backendUrl = import.meta.env.VITE_API_URL || "http://localhost:8081";
        const res = await fetch(`${backendUrl}/api/audit?url=${encodeURIComponent(url)}`);
        const json = await res.json();

        if (!res.ok) {
          addHistory({ type: 'error', text: `[FAILED] ${json.error || "An unknown server error occurred."}` });
        } else {
          addHistory({ type: 'success', text: '[SUCCESS] Audit Complete. Report:' });
          addHistory({ type: 'text', text: `  HTTP Status        : ${json.httpStatus}` });
          addHistory({ type: 'text', text: `  Response Time      : ${json.responseTimeMs} ms` });
          addHistory({ type: 'text', text: `  Page Title         : ${json.pageTitle || 'N/A'}` });
          addHistory({ type: 'text', text: `  Meta Description   : ${json.metaDescription || 'N/A'}` });
          addHistory({ type: 'text', text: `  H1 Tag Count       : ${json.h1Count}` });
          addHistory({ type: 'text', text: `  Images Missing Alt : ${json.imagesMissingAltCount}`, className: json.imagesMissingAltCount > 0 ? 'text-yellow-400' : 'text-gray-300' });
          addHistory({ type: 'text', text: `  Approx. Word Count : ${json.wordCount}` });
        }
      } catch (err) {
        addHistory({ type: 'error', text: '[FAILED] Network error. Ensure the backend server is running.' });
      } finally {
        setIsProcessing(false);
      }
      return;
    }

    addHistory({ type: 'error', text: `Command not found: ${command}. Type 'help' for available commands.` });
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleCommand(input);
      setInput('');
    }
  };

  return (
    <div className="min-h-screen bg-black text-gray-300 font-mono p-4 sm:p-8 text-sm sm:text-base selection:bg-green-700 selection:text-white">
      <div className="max-w-4xl mx-auto flex flex-col gap-1">
        
        {history.map((item, index) => {
          if (item.type === 'art') {
            return (
              <pre key={index} className="text-green-500 font-bold mb-4 whitespace-pre-wrap leading-tight text-[10px] sm:text-sm">
                {item.text}
              </pre>
            );
          }
          if (item.type === 'command') {
            return <div key={index} className="text-gray-100">{item.text}</div>;
          }
          if (item.type === 'error') {
            return <div key={index} className="text-red-500">{item.text}</div>;
          }
          if (item.type === 'success') {
            return <div key={index} className="text-green-400 mt-2">{item.text}</div>;
          }
          if (item.type === 'info') {
            return <div key={index} className="text-blue-400">{item.text}</div>;
          }
          return (
            <div key={index} className={item.className || "text-gray-300 whitespace-pre-wrap"}>
              {item.text}
            </div>
          );
        })}

        <div className="flex items-center mt-2">
          <span className="text-green-500 mr-2 shrink-0">guest@pagepulse:~$</span>
          <input
            ref={inputRef}
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            onKeyDown={handleKeyDown}
            disabled={isProcessing}
            className="flex-grow bg-transparent outline-none border-none text-gray-100 font-mono"
            spellCheck="false"
            autoComplete="off"
            autoFocus
          />
        </div>
        
        {isProcessing && (
          <div className="text-gray-400 animate-pulse mt-1">_</div>
        )}

        <div ref={endOfTerminalRef} />
      </div>
    </div>
  );
}

export default App;
