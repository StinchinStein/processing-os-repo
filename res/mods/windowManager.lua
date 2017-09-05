local M = {}

local alreadyDragging = false
local windows = javaEngine.windows

function M.draw()
	for i = windows:size()-1,0,-1 do
		local w = windows:get(i)
		
		updateWindow(i, w)
		drawWindowFrame(w)
		--w:draw()
		javaEngine:hookMethod("drawWindow", { w })
	end
end

function updateWindow(i, w)
	w.id = i
	if(javaEngine.mousePressed and javaEngine:mouseInArea(w.x, w.y, w.w, w.h) and not alreadyDragging) then
		w.dragging = true
		alreadyDragging = true
	elseif(not javaEngine.mousePressed) then
		w.dragging = false
		alreadyDragging = false
	end
	if(w.dragging) then
		w.x = w.x + (javaEngine.mouseX-javaEngine.pmouseX)
		w.y = w.y + (javaEngine.mouseY-javaEngine.pmouseY)
	end
end

function drawWindowFrame(w)
	javaEngine:noStroke()
	javaEngine:fill(0, 0, 0, 20)
	for k = 0,5 do
		javaEngine:rect(w.x - k, w.y - k, w.w + (k*2), w.h + (k*2), 10);
	end

	javaEngine:clip(w.x, w.y, w.w, w.h)
		drawBlurBox(w.x, w.y, w.w, 18)
		javaEngine:fill(255, 255, 255, 50);
		javaEngine:rect(w.x, w.y, w.w, 18);
		
		javaEngine:fill(0);
		javaEngine:text(w.title .. " | ".. w.id, w.x + 2, w.y + 13);

		javaEngine:fill(255, 255, 255, 255);
		javaEngine:rect(w.x, w.y + 18, w.w, w.h - 18, 5);
		javaEngine:rect(w.x, w.y + 18, w.w, 4);
	javaEngine:noClip();
end


return M