
Date d = new Date()
String dt = d.format("yyyy-MM-dd")

def props = [:]

props.doctitle = ask("Title of your document [Example]", "Example", "doctitle")
props.docdate = ask("Date of your document [$dt]", "$dt", "docdate")

def titlepageflag = ask("Title page? [no]", "no", "titlepageflag")

if(titlepageflag && titlepageflag.equals('yes') || titlepageflag.equals('true')) {
    props.titlepageoption = ""
} else {
    props.titlepageoption = ":notitle:"
}


processTemplates 'src/asciidoc/main.adoc', props


