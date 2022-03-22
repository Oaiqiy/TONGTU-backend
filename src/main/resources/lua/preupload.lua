local tempK = KEYS[1]
-- uploading value key

local filesK = KEYS[2]
-- files hash map key

local usedK = KEYS[3]
-- used value key

local size = tonumber(ARGV[1])
-- file size

local MD5 = ARGV[2]
-- file MD5

local max = tonumber(ARGV[3])
-- user's max storage

local uploading = redis.call("GET",temp)

if uploading == false then
    uploading = 0
end

local used = redis.call("GET",usedK);

if(redis.call("HEXISTS",files,MD5) == 1) then
    return 2
end

local total = uploading + used + size;

if(total > max) then
    return 1
end

redis.call("SET",temp,uploading + size)

redis.call("HSET",files,MD5,size)

return 0