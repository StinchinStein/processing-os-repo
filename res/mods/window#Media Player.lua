local w = javaEngine:createWindow(20, 20, "Task Manager")

function initMethod()
	javaEngine:commitWindow(w)
end


function drawWindow(window)
	if(window.title == "Task Manager") then
		javaEngine:fill(0)
		for i = 0, javaEngine.scripts:size() do
			local script = javaEngine.scripts:get(i)
			javaEngine:text(script.scriptFileName, window.x + 2, (i*15) + window.y + 30)
		end
	end
end

function updateMethod()
	
end
