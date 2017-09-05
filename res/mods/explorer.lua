BG = nil

local windowManager = require("res/mods/windowManager")

function initMethod()
	BG = javaEngine:loadImage("img0.jpg")
	BG:resize(800, 470)
end


function drawMethod()
	javaEngine:background(BG)

	windowManager.draw()

	--Taskbar
	drawBlurBox(0, javaEngine.height - 25, javaEngine.width, 25, 2)
	javaEngine:fill(50, 50, 50, 50)
	javaEngine:rect(0, javaEngine.height - 25, javaEngine.width, 25)
end

local blur
function drawBlurBox(x, y, w, h, intensity)
	intensity = intensity or 1
	for i = 0, intensity do
		if(blur == nil) then
			blur = javaEngine:createGraphics(w, h+1)
		end
		blur = javaEngine:get(x, y, w, h+1)
		javaEngine:fastSmallBlur(blur, blur)

		javaEngine:image(blur, x, y)
	end
end