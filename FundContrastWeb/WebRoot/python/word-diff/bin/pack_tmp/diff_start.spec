# -*- mode: python -*-

block_cipher = None


a = Analysis(['diff_start.py'],
             pathex=['C:\\Users\\huangzhouzhou\\Documents\\GitHub\\word-diff\\bin\\pack_tmp'],
             binaries=[],
             datas=[(r'C:\Users\huangzhouzhou\Documents\GitHub\word-diff\bin\pack_tmp\dist\default.docx','docx')],
             hiddenimports=[],
             hookspath=[],
             runtime_hooks=[],
             excludes=[],
             win_no_prefer_redirects=False,
             win_private_assemblies=False,
             cipher=block_cipher,
             noarchive=False)
pyz = PYZ(a.pure, a.zipped_data,
             cipher=block_cipher)
exe = EXE(pyz,
          a.scripts,
          a.binaries,
          a.zipfiles,
          a.datas,
          [],
          name='diff_start',
          debug=False,
          bootloader_ignore_signals=False,
          strip=False,
          upx=True,
          runtime_tmpdir=None,
          console=True )