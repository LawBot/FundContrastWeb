from argparse import ArgumentParser, SUPPRESS, HelpFormatter
import datetime
from glob import glob
import os
import re
import subprocess
import sys
import win32com.client as win32
from win32com.client import constants


def accept_changes(inputs, new_file=None, outdir=None, verbose=True):
    """Accept all changes in a MS Word document"""
    # Several files or directory parsed as argument
    if isinstance(inputs, list):
        for input in inputs:
            accept_changes(input, None, outdir, verbose)
        return None
    elif os.path.isdir(inputs):
        for dir, subdirs, files in os.walk (inputs):
            for name in files:
                path = os.path.join (dir, name).replace ('\\', '/')
                if '~$' not in path and re.search(r'\.(docx?|rtf)$', path):
                    accept_changes(path, None, outdir, verbose)
        return None
    else:
        pass
    # Get absolute paths of files
    doc_file_abs = os.path.abspath(inputs)
    new_file_abs = os.path.abspath(new_file) if new_file else doc_file_abs
    new_file_abs = re.sub(r'\.\w+$', '.docx', new_file_abs)
    if outdir is not None:
        if not os.path.exists(outdir):
            os.mkdir(outdir)
        path = outdir + '/' + re.sub(r'.*[/\\]', '', new_file_abs)
        new_file_abs = os.path.abspath(path)
    # Check
    if not os.path.isfile(doc_file_abs):
        print("ERROR: input file '%s' cannot be found" % doc_file_abs)
        return None
    # Opening MS Word
    word = win32.gencache.EnsureDispatch('Word.Application')
    doc = word.Documents.Open(doc_file_abs)
    doc.Activate ()
    if verbose:
        print("File '%s' has been opened" % inputs)


    # Accept all changes
    if verbose:
        print ("Accepting all changes")
    word.ActiveDocument.TrackRevisions = False
    try:
        word.WordBasic.AcceptAllChangesInDoc ()
    except TypeError:
        pass

    # Save and Close
    try:
        if new_file_abs == doc_file_abs:
            word.ActiveDocument.Save()
            if verbose:
                print("Document '%s' saved" % inputs)
        else:
            word.ActiveDocument.SaveAs(
                new_file_abs, FileFormat=constants.wdFormatXMLDocument
            )
            if verbose:
                print("Document saved to '%s'" % (new_file or new_file_abs))
    except Exception as e:
        print("ERROR while trying to save '%s': %s" % (new_file_abs, e))
    doc.Close(False)

    # Return path of updated file
    return new_file_abs


def main():
    # -Information on the Program-#
    copyright_year = 2017
    prog = "accept_docx_changes"
    version_string = "%s v1.0" % prog
    help_string = """\
    Purpose: Accept all changes in a MS Word document and save (as docx).
    """

    # -Parse options-#
    parser = ArgumentParser (
        description=help_string, prog=prog,
        formatter_class=lambda prog: HelpFormatter (prog, max_help_position=30)
    )

    # Configuration(s) for Traceability
    parser.add_argument (
        "inputs", nargs="+",
        help="Files we want to accept changes"
    )

    # Output file
    parser.add_argument (
        "-o", "--output",
        action="store", default=None,
        metavar="FILE", help="Name of output file"
    )
    parser.add_argument (
        "-d", "--outdir",
        action="store", default=None,
        metavar="DIR", help="Name of output direcory"
    )

    # Open if we have transformed one file
    parser.add_argument (
        "-p", "--open", action='store_true', default=False,
        help="Open Saved file after accepting all changes"
    )

    # Version
    parser.add_argument (
        "--version", action='store_true', dest="print_version",
        help="print version of %s" % prog
    )

    # Verbose mode
    parser.add_argument (
        "-q", "--quiet",
        action="store_true", default=False,
        help="Hide message of each operation done"
    )

    # Parsing options
    global options
    options = parser.parse_args ()

    # Let's start
    msg = "%s started at %s"
    if not options.quiet:
        ctime = datetime.datetime.now ().ctime ()
        print (msg % (version_string, ctime))

    # Let's get into business and do our stuff
    output = None if len(options.inputs) > 1 else options.output
    verbose = not options.quiet
    out = accept_changes(options.inputs, output, options.outdir, verbose)

    # And we are done!
    msg = "%s finished at %s"
    if not options.quiet:
        ctime = datetime.datetime.now ().ctime ()
        print (msg % (version_string, ctime))

    # Let's look at what we did
    if out is not None and options.open:
        if sys.platform.startswith ('darwin'):
            subprocess.call (('open', out))
        elif os.name == 'nt':
            os.startfile (out)
        elif os.name == 'posix':
            subprocess.call (('xdg-open', out))

if __name__ == '__main__':
    main ()
