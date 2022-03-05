local name = KEYS[1]
local md5 = KEYS[2]
local size = ARGV[1]
local used = ARGV[2]
local max = ARGV[3]

if redis.call("get", name+":used") == false then
    redis.call("set", name+":used",used)
else
    used = redis.call("get",name+"used")
end

if(used + size > max) then
    return false
end

redis.call("hset",name+":files",md5,size)

return true