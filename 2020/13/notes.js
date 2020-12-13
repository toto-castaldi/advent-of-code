const schedule = (text) => {
    [earliest, bustServiceLine] = text.trim().split("\n");
    earliest = parseInt(earliest.trim());
    const busService = [];
    for (let busId of bustServiceLine.trim().split(",")) {
        if (busId !== "x") busService.push(parseInt(busId));
    }
    return { earliest, busService }
}

const search = (departureTime, busService) => {
    for (busId of busService) if (departureTime % busId === 0) return busId;
}

const nextBus = (sched) => {
    let time = sched.earliest;
    let id = search(time, sched.busService)
    while (id === undefined) id = search(++time, sched.busService);
    return { id, time }
}

module.exports = {
    schedule, nextBus, search
}