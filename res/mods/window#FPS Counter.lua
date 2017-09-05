
local w = javaEngine:createWindow(20, 220, "FPS Counter")

function initMethod()
	javaEngine:commitWindow(w)
end


function drawWindow(window)
	if(window.title == "FPS Counter") then
		javaEngine:fill(0)
		javaEngine:text(math.floor(javaEngine.frameRate) .."fps\nMouse X/Y: ".. javaEngine.mouseX .."/".. javaEngine.mouseY, window.x, window.y + 30)
	end
end

function updateMethod()
	
end