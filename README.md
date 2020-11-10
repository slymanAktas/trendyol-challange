# trendyol-challange
1. Test çalıştırıldığında şayet chromedriver ile chrome version uyuşmama hatası alınır ise 
aşağıda yer alan class içerisinden testlerde kullanılacak olan chromedriver'in versionu seçilebilir

`path/to/project/TrendyolChallange/src/main/java/ui/browser/browsertypes/chrome/Chrome.java`

2. UI testcase'sinde yükleneyen image'ları test içerisinde consola log'ladım.

3. Browser seçimi için `Run/Debug Configurations --> VM Options`kısmından `-Dbrowser=chrome` parametresi geçilmelidir.
Bu parametre geçilmediği taktirde default chrome kullannılmakradır.
Son olarak ilk test case için proxy driver, chrome destekli olduğu için testi koşarken bu default'da gelen chrome üzerinden test 
koşulması rica olunur.
