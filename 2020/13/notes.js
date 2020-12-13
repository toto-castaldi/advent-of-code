const schedule = (text) => {
    [earliest, bustServiceLine] = text.trim().split("\n");
    if (bustServiceLine !== undefined) {
        earliest = parseInt(earliest.trim());
    } else {
        bustServiceLine = earliest;
        earliest = undefined;
    }
    const busService = [];
    let offest = 0;
    let maxBusInfo = { busId: 0 };
    for (let busId of bustServiceLine.trim().split(",")) {
        const busInfo = { busId: parseInt(busId), offest };
        if (busId !== "x") busService.push(busInfo);
        offest++;
        if (busInfo.busId > maxBusInfo.busId) maxBusInfo = busInfo;
    }
    return { earliest, busService, maxBusInfo }
}

const search = (departureTime, busService) => {
    for (busInfo of busService) if (departureTime % busInfo.busId === 0) return busInfo.busId;
}

const nextBus = (sched) => {
    let time = sched.earliest;
    let id = search(time, sched.busService)
    while (id === undefined) id = search(++time, sched.busService);
    return { id, time }
}

const allMatches = (tZero, busService) => {
    for (bus of busService) {
        if ((tZero + bus.offest) % bus.busId !== 0) return false;
    }
    return true;
}

const earliestAllMatchesOffset = (sched) => {
    const last = sched.busService[sched.busService.length - 1];
    const first = sched.busService[0];
    const max = sched.maxBusInfo;

    let tMax = sched.busService.reduce((p, c, i) => p * c.busId, 1);
    while (true && tMax > 0) {
        if (allMatches(tMax - last.offest, sched.busService)) return tMax - last.offest;
        tMax -= last.busId;
    }
}

module.exports = {
    schedule, nextBus, search, earliestAllMatchesOffset, allMatches
}