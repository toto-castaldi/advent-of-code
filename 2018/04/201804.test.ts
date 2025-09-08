import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { GuardLog } from './201804.ts';

Deno.test("step one", () => {
  const guardLog = new GuardLog();
  guardLog.debug = true;
  
  guardLog.add("[1518-11-01 00:00] Guard #10 begins shift");
  guardLog.add("[1518-11-01 00:05] falls asleep");
  guardLog.add("[1518-11-01 00:25] wakes up");
  guardLog.add("[1518-11-01 00:30] falls asleep");
  guardLog.add("[1518-11-01 00:55] wakes up");
  guardLog.add("[1518-11-01 23:58] Guard #99 begins shift");
  guardLog.add("[1518-11-02 00:40] falls asleep");
  guardLog.add("[1518-11-02 00:50] wakes up");
  guardLog.add("[1518-11-03 00:05] Guard #10 begins shift");
  guardLog.add("[1518-11-03 00:24] falls asleep");
  guardLog.add("[1518-11-03 00:29] wakes up");
  guardLog.add("[1518-11-04 00:02] Guard #99 begins shift");
  guardLog.add("[1518-11-04 00:36] falls asleep");
  guardLog.add("[1518-11-04 00:46] wakes up");
  guardLog.add("[1518-11-05 00:03] Guard #99 begins shift");
  guardLog.add("[1518-11-05 00:45] falls asleep");
  guardLog.add("[1518-11-05 00:55] wakes up");

  assertEquals(guardLog.guardIdWithMostSleepOnMinute(), 10);
  assertEquals(guardLog.guardMostSleepedMinuteOfGuard(10), 24);
});