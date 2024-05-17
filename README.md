Plagiarism Web

# VnCoreNLP:

- word segmentation + pos tagging: 4s - 5s
- word segmentation : 3.5s - 4s
> Reduce time by 88%

# Proposed solutions

- Perform word segmentation and pos tagging at parsing time. Store in a json file with format
  - Reusable
  - Perform faster matching

- Data format:
```json
[
  {
    "page": 0,
    "raw_text": "...",
    "sentences": [
      {
        "tokens": [],
        "tags": []
      }
    ]
  }
]
```
When deserialization, program may encounter memory issue. Measure to see if memory is being used too much

So khớp tài liệu trong trường
Cơ sở dữ liệu
En to en
check 2 câu giống nhau nhưng không match

# UI and Backend

Original version:
- Java
- Springs

Modify:
- Kotlin
- Ktor

