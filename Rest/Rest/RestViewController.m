//
//  RestViewController.m
//  Rest
//
//  Created by SangChan on 2014. 7. 14..
//  Copyright (c) 2014년 Spring. All rights reserved.
//

#import "RestViewController.h"

@interface RestViewController ()

@end

@implementation RestViewController

- (IBAction)fetchGreeting;
{
    NSURL *url = [NSURL URLWithString:@"http://rest-service.guides.spring.io/greeting"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:[NSOperationQueue mainQueue]
                           completionHandler:^(NSURLResponse *response,
                                               NSData *data, NSError *connectionError)
     {
         if (data.length > 0 && connectionError == nil)
         {
             NSDictionary *greeting = [NSJSONSerialization JSONObjectWithData:data
                                                                      options:0
                                                                        error:NULL];
             self.greetingId.text = [[greeting objectForKey:@"id"] stringValue];
             self.greetingContent.text = [greeting objectForKey:@"content"];
         }
     }];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
