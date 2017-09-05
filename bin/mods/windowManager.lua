local M = {}

local windows = javaEngine.windows

function M.draw()
	for i = windows:size()-1,0,-1 do
		local window = windows:get(i)
		window:update()
		window:draw()
		javaEngine:hookMethod("drawWindow", { window })
		javaEngine:hookMethod("drawWindowFrame", { window })
	end
end


return M