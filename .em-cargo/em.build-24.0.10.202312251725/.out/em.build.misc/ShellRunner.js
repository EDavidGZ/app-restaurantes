const ShellRunner = exports;

with (exports) {

var em$attrs = exports.em$attrs = {};

// -------- IMPORTS -------- //


// -------- DIMENSIONS -------- //


// -------- DECLARATIONS -------- //

var em$exclude = exports.em$exclude = false;
var em$export = exports.em$export = false;
var em$traceGrp = exports.em$traceGrp = null;
var em$used = exports.em$used = false;
var em$tracePri = exports.em$tracePri = 0;
var em$fail = exports.em$fail = '&em_build_misc_ShellRunner::em$fail';
var em$halt = exports.em$halt = '&em_build_misc_ShellRunner::em$halt';
var em$reset = exports.em$reset = '&em_build_misc_ShellRunner::em$reset';
var em$run = exports.em$run = '&em_build_misc_ShellRunner::em$run';
var em$shutdown = exports.em$shutdown = '&em_build_misc_ShellRunner::em$shutdown';
var em$startup = exports.em$startup = '&em_build_misc_ShellRunner::em$startup';
var em$startupDone = exports.em$startupDone = '&em_build_misc_ShellRunner::em$startupDone';
exports.Job = class Job extends $$Agg { constructor(obj, cn) { super(cn); 
    this.status = 0;
    this.errlines = new $$Arr([], undefined, (cn) => null);
    this.outlines = new $$Arr([], undefined, (cn) => null);
    if (obj) this.$$asn(obj);
}};

// -------- FUNCTIONS -------- //

const exec = exports.exec = function(buildDir, cmd) {
    let job = new Job;
    let proc = $ChildProc.spawnSync(cmd, {cwd: buildDir, shell: em$session.getShellPath()});
    job.status = proc.status === null ? -1 : proc.status;
    job.errlines.$$asn(String(proc.stderr).split('\n').map(s => s.trim()));
    job.outlines.$$asn(String(proc.stdout).split('\n').map(s => s.trim()));
    return job;
}

exports.em$cname = 'em_build_misc_ShellRunner';
exports.em$isModule = true;
exports.em$upath = 'em.build.misc/ShellRunner';
exports.em$val_em$exclude = () => em$exclude;
exports.em$val_em$export = () => em$export;
exports.em$val_em$traceGrp = () => em$traceGrp;
exports.em$val_em$used = () => em$used;
exports.em$val_em$tracePri = () => em$tracePri;

};
