local temp = KEYS[1]
local files = KEYS[2]

local size = tonumber(ARGV[1])
local MD5 = ARGV[2]
local used = tonumber(ARGV[3])
local max = tonumber(ARGV[4])

local uploading = 0;

if redis.call("GET", temp) == true then
    uploading = redis.call("GET",temp)
end

if(redis.call("HEXISTS",files,MD5) == 1) then
    return 2
end

used = used + uploading + size

if(used > max) then
    return 1
end

redis.call("SET",temp,used)

redis.call("HSET",files,MD5,size)

return 0